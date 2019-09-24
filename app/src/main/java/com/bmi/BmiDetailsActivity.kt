package com.bmi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView

class BmiDetailsActivity : AppCompatActivity() {

    lateinit var back: ImageView
    lateinit var intBMI: TextView
    lateinit var fractBMI: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.bmi_details)

        intBMI = findViewById(R.id.int_part_of_BMI)
        fractBMI = findViewById(R.id.fractional_part_of_BMI)

        back = findViewById(R.id.back)
        back.setOnClickListener {
            onBackPressed()
        }
        var bmi:Double = 56.0/(1.73*1.73)
        var bmiResult:String=""
        when (bmi){
           in 1.0..15.9 -> bmiResult = "Weight deficit"
           in 16.0..16.99 -> bmiResult = "Underweight"
           in 17.0..18.49 -> bmiResult = "Low body weight"
           in 18.5..24.99 -> bmiResult = "Normal weight"
           in 25.0..29.99 -> bmiResult = "Overweight"
           in 30.0..34.99 -> bmiResult = "Obese I class"
           in 35.0..39.99 -> bmiResult = "Obese II class"
           in 40.0..100.0 -> bmiResult = "Obese III class"
        }
        intBMI.setText(bmiResult)
    }
}