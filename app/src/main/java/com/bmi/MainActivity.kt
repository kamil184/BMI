package com.bmi

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.NumberPicker


class MainActivity : AppCompatActivity() {

    lateinit var toolbar:Toolbar
    lateinit var picker:NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        picker = findViewById(R.id.number_picker)

        val data = arrayOf("Berlin", "Moscow", "Tokyo")

        picker.setMinValue(0)
        picker.setMaxValue(data.size - 1)
        picker.setDisplayedValues(data)
        toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.back)

        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onBackPressed()
            }
        })
    }
}
