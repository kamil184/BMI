package com.bmi

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class BmiDetailsActivity : AppCompatActivity() {

    lateinit var back: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.bmi_details)

        back = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
    }
}