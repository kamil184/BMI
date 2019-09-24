package com.bmi

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    lateinit var name: TextInputEditText
    lateinit var back: ImageView
    lateinit var calculate: AppCompatImageButton
    lateinit var weightPicker: NumberPicker
    lateinit var heightPicker: NumberPicker
    lateinit var genderPicker: NumberPicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.name)
        calculate = findViewById(R.id.calculate)
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

        val genders = arrayOf("male", "female")

        genderPicker.minValue = 1
        genderPicker.maxValue = 2
        genderPicker.displayedValues = genders

        back = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }

        calculate.setOnClickListener {
            val intent = Intent(this@MainActivity,BmiDetailsActivity::class.java)
            intent.putExtra("height",heightPicker.value)
            intent.putExtra("weight",weightPicker.value)
            intent.putExtra("gender",genderPicker.value)
            intent.putExtra("name",name.text)
            startActivity(intent)
        }
    }
}
