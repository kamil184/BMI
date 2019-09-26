package com.bmi

import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import java.io.File
import java.io.FileOutputStream
import java.util.*
import android.R.id.shareText
import android.content.pm.PackageManager
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import hotchemi.android.rate.OnClickButtonListener
import hotchemi.android.rate.AppRate


class BmiDetailsActivity : AppCompatActivity() {

    lateinit var share: Button
    lateinit var rate: Button
    lateinit var back: ImageView
    lateinit var intBMI: TextView
    lateinit var fractBMI: TextView
    lateinit var userMsg: TextView
    lateinit var rangeMsg: TextView
    lateinit var PI: TextView
    lateinit var mAdView: AdView

    lateinit var shareText: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.bmi_details)

        MobileAds.initialize(this) {}
        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        rate = findViewById(R.id.rateBtn)
        share = findViewById(R.id.shareBtn)
        intBMI = findViewById(R.id.int_part_of_BMI)
        fractBMI = findViewById(R.id.fractional_part_of_BMI)
        userMsg = findViewById(R.id.user_msg)
        rangeMsg = findViewById(R.id.bmiRange)
        PI = findViewById(R.id.ponderalIndex)
        back = findViewById(R.id.back)

        back.setOnClickListener {
            onBackPressed()
        }

        AppRate.with(this)
            .setInstallDays(0)
            .setLaunchTimes(0)
            .setRemindInterval(0)
            .setShowLaterButton(true)
            .setDebug(false)
            .setOnClickButtonListener { which ->
                Log.d(MainActivity::class.java.name, which.toString())
            }
            .monitor()

        val height: Int = intent.getIntExtra("height", 1)
        val weight: Int = intent.getIntExtra("weight", 1)
        val name: String = intent.getStringExtra("name")

        var bmi: Double =
            weight.toDouble() / ((height.toDouble() / 100) * (height.toDouble() / 100))
        var piValue =
            weight.toDouble() / ((height.toDouble() / 100) * (height.toDouble() / 100) * (height.toDouble() / 100))
        var bmiResult: String = ""
        var bmiRange: String = ""
        when (bmi) {
            in 1.0..15.9 -> {
                bmiResult = "Weight deficit"
                bmiRange = "less than 16 kg/m2"
            }
            in 16.0..16.99 -> {
                bmiResult = "Underweight"
                bmiRange = "16.0 kg/m2 - 16.99 kg/m2"
            }
            in 17.0..18.49 -> {
                bmiResult = "Low body weight"
                bmiRange = "17.0 kg/m2 - 18.49 kg/m2"
            }
            in 18.5..24.99 -> {
                bmiResult = "Normal weight"
                bmiRange = "18.5 kg/m2 - 25.00 kg/m2"
            }
            in 25.0..29.99 -> {
                bmiResult = "Overweight"
                bmiRange = "25.0 kg/m2 - 29.99 kg/m2"
            }
            in 30.0..34.99 -> {
                bmiResult = "Obese I class"
                bmiRange = "30.0 kg/m2 - 34.99 kg/m2"
            }
            in 35.0..39.99 -> {
                bmiResult = "Obese II class"
                bmiRange = "35.0 kg/m2 - 39.99 kg/m2"
            }
            in 40.0..100.0 -> {
                bmiResult = "Obese III class"
                bmiRange = "more than 40.0 kg/m2"
            }
        }
        val ponderalIndex = String.format("%.2f", piValue)
        intBMI.text = bmi.toInt().toString()
        fractBMI.text = "." + ((bmi - bmi.toInt()) * 100).toInt().toString()
        userMsg.text = getString(R.string.user_msg, name, bmiResult)
        rangeMsg.text = getString(R.string.bmi_range_msg, bmiResult, bmiRange)
        PI.text = getString(R.string.ponderal_index, ponderalIndex)

        val bmiFormatted = String.format("%.2f",bmi)
        shareText = "BMIapp calculated my BMI, $bmiFormatted, and ponderal index: $ponderalIndex! It’s $bmiResult. Try it!"
        share.setOnClickListener {
            checkPermissions()
        }
        rate.setOnClickListener{
            AppRate.with(this).showRateDialog(this)
        }
    }

    private fun share(){
        val rootView = window.decorView.findViewById<View>(android.R.id.content)
        shareImage(store(getScreenShot(rootView), Date().toString()+".jpg"))
    }

    @Suppress("DEPRECATION")
    fun getScreenShot(view: View): Bitmap {
        val screenView = view.rootView
        screenView.isDrawingCacheEnabled = true
        val bitmap = Bitmap.createBitmap(screenView.drawingCache)
        screenView.isDrawingCacheEnabled = false
        return bitmap
    }

    private fun store(bm: Bitmap, fileName: String):File? {
        val dirPath = Environment.getExternalStorageDirectory().absolutePath + "/Screenshots"
        val dir = File(dirPath)
        if (!dir.exists())
            dir.mkdirs()
        val file = File(dirPath, fileName)
        try {
            val fOut = FileOutputStream(file)
            bm.compress(Bitmap.CompressFormat.PNG, 85, fOut)
            fOut.flush()
            fOut.close()
            return file
        } catch (e: Exception) {
            return null
            e.printStackTrace()
        }
    }

    private fun shareImage(file: File?) {
        if(file != null) {
            val uri = Uri.fromFile(file)
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "image/jpg"
            intent.putExtra(Intent.EXTRA_TEXT, shareText)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            try {
                startActivity(Intent.createChooser(intent, "Share"))
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun checkPermissions(){
        val permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
            share()
        } else {
            ActivityCompat.requestPermissions(this,  arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    share()
                } else {
                    Toast.makeText(this, "You must allow memory access in order to share", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}
