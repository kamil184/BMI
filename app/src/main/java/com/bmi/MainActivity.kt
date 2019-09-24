package com.bmi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.View
import android.view.WindowManager
import android.widget.NumberPicker


class MainActivity : AppCompatActivity() {

    lateinit var toolbar:Toolbar
    lateinit var weightPicker:NumberPicker
    lateinit var heightPicker:NumberPicker
    lateinit var genderPicker:NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)

        weightPicker = findViewById(R.id.weight_picker)
        heightPicker = findViewById(R.id.height_picker)
        genderPicker = findViewById(R.id.gender_picker)

        weightPicker.wrapSelectorWheel = false
        heightPicker.wrapSelectorWheel = false
        genderPicker.wrapSelectorWheel = false


        weightPicker.minValue = 20
        weightPicker.maxValue = 200

        heightPicker.minValue = 50
        heightPicker.maxValue = 250

        val genders = arrayOf("male","female")

        genderPicker.minValue = 1
        genderPicker.maxValue = 2
        genderPicker.displayedValues = genders


        toolbar = findViewById(R.id.toolbar)

        toolbar.setNavigationIcon(R.drawable.back)

        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                onBackPressed()
            }
        })
    }
}
