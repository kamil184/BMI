package com.bmi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.InterstitialAd
import com.google.android.gms.ads.MobileAds
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var nameEditText: TextInputEditText
    lateinit var back: ImageView
    lateinit var calculate: AppCompatImageButton
    lateinit var weightPicker: NumberPicker
    lateinit var heightPicker: NumberPicker
    lateinit var genderPicker: NumberPicker
    lateinit var linearLayout: LinearLayout
    private lateinit var mInterstitialAd: InterstitialAd

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        mInterstitialAd = InterstitialAd(this)
        mInterstitialAd.adUnitId = "ca-app-pub-3940256099942544/1033173712"
        mInterstitialAd.loadAd(AdRequest.Builder().build())

        nameEditText = findViewById(R.id.name)
        calculate = findViewById(R.id.calculate)
        weightPicker = findViewById(R.id.weight_picker)
        heightPicker = findViewById(R.id.height_picker)
        genderPicker = findViewById(R.id.gender_picker)
        linearLayout = findViewById(R.id.linear)

        weightPicker.wrapSelectorWheel = false
        heightPicker.wrapSelectorWheel = false
        genderPicker.wrapSelectorWheel = false

        weightPicker.minValue = 20
        weightPicker.maxValue = 200
        weightPicker.value = 65

        heightPicker.minValue = 50
        heightPicker.maxValue = 250
        heightPicker.value = 175

        val genders = arrayOf("male", "female")

        genderPicker.minValue = 1
        genderPicker.maxValue = 2
        genderPicker.displayedValues = genders

        back = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }

        calculate.setOnClickListener {
            val name = nameEditText.text.toString()
            if (name.isNotEmpty()) {
                if (mInterstitialAd.isLoaded) {
                    mInterstitialAd.show()
                } else {
                    Log.d("CalculateAd", "The interstitial wasn't loaded yet.")
                    Snackbar.make(linearLayout, "Error", Snackbar.LENGTH_SHORT).show()
                }
            }else {Snackbar.make(linearLayout, "Please enter a name", Snackbar.LENGTH_SHORT).show()}
        }


        mInterstitialAd.adListener = object: AdListener() {
            override fun onAdClicked() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
                val name = nameEditText.text.toString()
                val intent = Intent(this@MainActivity, BmiDetailsActivity::class.java)
                intent.putExtra("height", heightPicker.value)
                intent.putExtra("weight", weightPicker.value)
                intent.putExtra("gender", genderPicker.value)
                intent.putExtra("name", name)
                startActivity(intent)
                // When the user clicks on an ad.
            }
            override fun onAdFailedToLoad(errorCode: Int) {
                Log.d("AdLoading", "Error loading $errorCode")
            }
            override fun onAdClosed() {
                mInterstitialAd.loadAd(AdRequest.Builder().build())
                val name = nameEditText.text.toString()
                val intent = Intent(this@MainActivity, BmiDetailsActivity::class.java)
                intent.putExtra("height", heightPicker.value)
                intent.putExtra("weight", weightPicker.value)
                intent.putExtra("gender", genderPicker.value)
                intent.putExtra("name", name)
                startActivity(intent)
                // when interstitial ad is closed.
            }
        }
    }

}
