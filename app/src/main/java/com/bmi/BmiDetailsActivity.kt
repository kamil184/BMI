package com.bmi

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import android.R.id.shareText
import androidx.core.app.ShareCompat
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



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
        intBMI.text = bmi.toInt().toString()
        fractBMI.text = "." + ((bmi - bmi.toInt()) * 100).toInt().toString()
        userMsg.text = getString(R.string.user_msg, name, bmiResult)
        rangeMsg.text = getString(R.string.bmi_range_msg, bmiResult, bmiRange)
        PI.text = getString(R.string.ponderal_index, String.format("%.2f", piValue))

        val shareText = "Hello, app BMI"
        share.setOnClickListener {
            val shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(shareText)
                .intent
            if (shareIntent.resolveActivity(packageManager) != null) {
                startActivity(shareIntent)
            }
        }
    }
}
