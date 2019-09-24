package com.bmi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.view.WindowManager

class BmiDetailsActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
             window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)


        setContentView(R.layout.bmi_details)
        toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.back)

        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onBackPressed()
            }
        })
    }
}