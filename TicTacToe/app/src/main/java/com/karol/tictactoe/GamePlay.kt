package com.karol.tictactoe

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.game_play.*


class GamePlay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_play)

        val screenWidth = getResources().getDisplayMetrics().widthPixels
        val screenHight = getResources().getDisplayMetrics().heightPixels

        val blockSize: Int
        if (screenHight > screenWidth )
            {
               blockSize = screenWidth / 10
            }
        else {
             blockSize = screenHight / 10
            }
        for (x in 1..10) {
            val row = LinearLayout(this)
            row.layoutParams = ViewGroup.LayoutParams(10*blockSize, blockSize)
            board.addView(row)
            row.id=x
            for (y in 1..10)
            {
                 val myButton = Button(this)
                 row.addView(myButton)
                  myButton.text = ""
                  myButton.layoutParams.height = (blockSize * 0.9).toInt()
                  myButton.layoutParams.width = (blockSize * 0.9).toInt()
                myButton.setOnClickListener{
                    myButton.text = "x"
                }
                }
            }

        button_exit.setOnClickListener{
            finish()
        }
    }
}

