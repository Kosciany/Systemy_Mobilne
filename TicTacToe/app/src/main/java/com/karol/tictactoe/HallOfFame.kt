package com.karol.tictactoe

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import kotlinx.android.synthetic.main.hall_of_fame.*


class HallOfFame : AppCompatActivity() {

    private val topScoreCount = 3
    private val topScore = IntArray(topScoreCount+1)
    private val preferenceFileName = "MyPref"
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.hall_of_fame)

        val sharedPref = this.getSharedPreferences(preferenceFileName,Context.MODE_PRIVATE)
        val text = sharedPref.getInt("top1", 0).toString()
        top_1_text.text = sharedPref.getInt("top1", 0).toString()
        top_2_text.text = sharedPref.getInt("top2", 0).toString()
        top_3_text.text = sharedPref.getInt("top3", 0).toString()

        exit.setOnClickListener {
            finish()
            }
        }

    fun saveResult(score: Int, activity: Activity) {
        val sharedPref = activity.getSharedPreferences(preferenceFileName,Context.MODE_PRIVATE)
        for (i in 0 until topScoreCount)
            {
                val key = "top$i"
                topScore[i] = sharedPref.getInt(key, 0)
            }

        topScore[topScoreCount] = score
        topScore.sort()
        topScore.reverse()

        val editor = sharedPref.edit()
        editor.putInt("top1",topScore[0] )
        editor.putInt("top2",topScore[1] )
        editor.putInt("top3",topScore[2] )
        editor.commit()

        /*
        top_1.text = topScore[0].toString()
        top_2.text = topScore[1].toString()
        top_3.text = topScore[2].toString()
        */


    }
}