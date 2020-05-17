package com.karol.imagepuzzle

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.ImageView

class MainActivity : AppCompatActivity() {

   private lateinit var pieces : ArrayList<Bitmap>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       val layout = findViewById<ConstraintLayout>(R.id.puzzlePile)



    }
}
