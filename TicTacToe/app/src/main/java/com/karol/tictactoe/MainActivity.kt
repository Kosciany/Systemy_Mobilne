package com.karol.tictactoe

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import kotlinx.android.synthetic.main.main_menu.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_menu)

        singlePlay.setOnClickListener {
            val intent = Intent(this, GamePlay::class.java)
            startActivity(intent)
        }

        multiPlay.setOnClickListener() {
            ;
        }

        hallOffFame.setOnClickListener{
            val intent = Intent(this,HallOfFame::class.java)
            startActivity(intent)
        }


        exit.setOnClickListener{
            finish()
            exitProcess(0)
        }
    }
}