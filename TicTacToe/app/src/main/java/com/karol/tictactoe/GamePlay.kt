package com.karol.tictactoe

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout

import com.karol.tictactoe.R.layout
import com.karol.tictactoe.HallOfFame
import kotlinx.android.synthetic.main.game_play.*
import kotlin.random.Random.Default.nextInt


class GamePlay : AppCompatActivity() {
    private val blockCount = 10
    private val winnerCombo = 5
    private val maxDepth = 5
    private val depthReachedValue = 1

    companion object {
        private const val blockCount = 10
        private val buttons = Array(blockCount) { IntArray(blockCount)}
        private var player1Turn = true
        private var player1Points = 0
        private var player2Points = 0
    }

    private val buttonsID = Array(blockCount) {IntArray(blockCount)}


    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun getStatusBarHeight(): Int {
        var statusBarHeight = 0
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            statusBarHeight = resources.getDimensionPixelSize(resourceId)
        }
        return statusBarHeight
    }

    fun checkWinner(buttonArray: Array<IntArray>, arraySize: Int, winningCombo: Int): Int {
        for (x in 0 until arraySize) {
            for (y in 0 until arraySize) {
                if (buttonArray[x][y] != 0) {
                    val valueToCheck = buttonArray[x][y]

                    //check vertically
                    var testValue = true
                    for (i in 1 until winningCombo) {
                        if (x + i >= arraySize || y >= arraySize) {
                            testValue = false
                            break
                        }
                        if (buttonArray[x + i][y] != valueToCheck) {
                            testValue = false
                        }
                    }
                    if (testValue) {
                        return valueToCheck
                    }

                    //check horizontally

                    testValue = true
                    for (i in 1 until winningCombo) {
                        if (x >= arraySize || y + i >= arraySize) {
                            testValue = false
                            break
                        }
                        if (buttonArray[x][y + i] != valueToCheck) {
                            testValue = false
                        }
                    }
                    if (testValue) {
                        return valueToCheck
                    }

                    //check diagonally

                    testValue = true
                    for (i in 1 until winningCombo) {
                        if (x + i >= arraySize || y + i >= arraySize) {
                            testValue = false
                            break
                        }
                        if (buttonArray[x + i][y + i] != valueToCheck) {
                            testValue = false
                        }
                    }
                    if (testValue) {
                        return valueToCheck
                    }

                    testValue = true
                    for (i in 1 until winningCombo) {
                        if (y + i >= arraySize || x - i < 0) {
                            testValue = false
                            break
                        }
                        if (buttonArray[x - i][y + i] != valueToCheck) {
                            testValue = false
                        }
                    }
                    if (testValue) {
                        return valueToCheck
                    }
                }
            }
        }
        return 0
    }

    private fun updatePoints()
        {
            if (player1Points < 99)
            {
                text_view_p1.text = "Player 1: " + player1Points.toString()
            }
            else
            {
                text_view_p1.text = "Player 1: 99+"
            }

            if (player2Points < 99)
            {
                text_view_p2.text = "Player 2: " + player2Points.toString()
            }
            else
            {
                text_view_p2.text = "Player 2: 99+"
            }


            val fameHandler = HallOfFame()
            if (player1Points > player2Points)
                {fameHandler.saveResult(player1Points,this)}
            else
              {fameHandler.saveResult(player2Points,this)}



        }
    private fun resetPoints()
        {
            player1Turn = true
            player1Points = 0
            player2Points = 0
        }

    private fun resetBoard(buttonArray: Array<IntArray>, arraySize: Int)
        {
            for (i in 0 until arraySize) {
                for (j in 0 until arraySize) {
                    buttonArray[i][j] = 0
                    val button =  findViewById<Button>(buttonsID[i][j])
                    button.text = " "
                }
            }
        }

    fun isAnyMoveLeft(buttonArray: Array<IntArray>, arraySize: Int): Boolean {
            for (i in 0 until arraySize) {
                for (j in 0 until arraySize) {
                    if (buttonArray[i][j] == 0) {
                        return true
                    }
                }
            }
            return false
        }

    private fun minMax(TempArray: Array<IntArray>, Depth: Int, MaxMove: Boolean, arraySize: Int, winningCombo: Int): Int {
            Log.i("MinMaxTag", "Running: Depth: $Depth")
            val score = checkWinner(TempArray,arraySize,winningCombo)
            if (score == 1)
                {
                    //X has won
                    return score
                }
            if (score == -1)
                {
                    // O has won
                    return score
                }
            if (Depth >= maxDepth)
                {
                    return depthReachedValue
                }
            if (!isAnyMoveLeft(TempArray,arraySize))
                {
                    return 0
                }
            if (MaxMove)
                {

                for (i in 0 until blockCount) {
                    for (j in 0 until blockCount) {
                        if (TempArray[i][j] == 0) {
                            TempArray[i][j] = 1
                            val temp = minMax (TempArray,Depth+1, !MaxMove,arraySize,winningCombo)
                            TempArray[i][j] = 0
                            if (temp == 1)
                                return temp
                            }
                        }
                    }
                }

            else
            {
                for (i in 0 until blockCount) {
                    for (j in 0 until blockCount) {
                        if (TempArray[i][j] == 0) {
                            TempArray[i][j] = (-1)
                            val temp = minMax (TempArray,Depth+1, !MaxMove,arraySize,winningCombo)
                            TempArray[i][j] = 0
                            if (temp == -1)
                            {
                                return temp
                            }

                        }
                    }
                }

            }
        return 0
        }

    private fun findBestMove(buttonArray: Array<IntArray>,arraySize: Int,winningCombo: Int)
        {
            val buttonsCopy = buttonArray.copyOf()
            var bestVal = 2 //worst case
            var moveColumn = -1
            var moveRow = -1

            for (i in 0 until arraySize) {
                for (j in 0 until arraySize) {
                    if (buttonsCopy[i][j] == 0)
                        {
                            buttonsCopy[i][j] = (-1)
                            val moveVal = minMax(buttonsCopy,0,true,arraySize,winningCombo)
                            buttonsCopy[i][j] = 0
                            if (moveVal < bestVal)
                                {
                                    //draw or win

                                    bestVal = moveVal
                                    moveColumn = i
                                    moveRow = j
                                    if (moveVal == -1)
                                        {
                                            buttonArray[moveColumn][moveRow] = -1
                                            findViewById<Button>(buttonsID[moveColumn][moveRow]).text = "O"
                                            return
                                        }
                                }
                        }
                }
            }

        return
        }

    private fun makeRandomMove(buttonArray: Array<IntArray>,arraySize: Int)
        {
            while (true)
            {
                val i = nextInt(0, arraySize)
                val j = nextInt(0, arraySize)
                if (buttonArray[i][j] == 0)
                    {
                        buttonArray[i][j] = -1
                        findViewById<Button>(buttonsID[i][j]).text = "O"
                        return
                    }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(layout.game_play)
        hideActionBar()

        val gameType = intent.getStringExtra("GameType")

        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val blockSize: Int
        blockSize = if (screenHeight > screenWidth ) {
            (screenWidth / blockCount)
        } else {
            ((screenHeight-getStatusBarHeight()) / blockCount)
        }



        for (x in 0 until blockCount) {
            val row = LinearLayout(this)
            row.layoutParams = ViewGroup.LayoutParams(screenHeight, blockSize)
            board.addView(row)
            row.id=x
            for (y in 0 until blockCount)
                {
                val myButton = Button(this)
                row.addView(myButton)
                myButton.id = View.generateViewId()


                myButton.layoutParams.height = (blockSize)
                myButton.layoutParams.width = (blockSize)
                buttonsID[x][y] = myButton.id
                myButton.setTextSize(TypedValue.COMPLEX_UNIT_PX, (0.25*blockSize).toFloat());

                if (buttons[x][y]==1) {
                    myButton.text = "X"
                }
                else if (buttons[x][y]== (-1)){
                    myButton.text = "O"
                }
                myButton.setOnClickListener {

                    if (buttons[x][y] == 0) {
                        if (player1Turn) {
                            myButton.text = "X"
                            buttons[x][y] = 1
                        } else {
                            myButton.text = "O"
                            buttons[x][y] = -1
                        }
                        if (gameType == "multiplayer")
                            {player1Turn = !player1Turn}
                        else
                            {
                                if (checkWinner(buttons, blockCount, winnerCombo) == 0)
                                {
                                    //findBestMove()
                                    makeRandomMove(buttons,blockCount)

                                }
                            }

                        val result = checkWinner(buttons,blockCount, winnerCombo)
                        if (result != 0) {
                            resetBoard(buttons,blockCount)

                            if (result == 1) {
                                player1Points += 1
                                if (gameType != "singleplayer")
                                {
                                    player1Turn = false
                                }
                            }

                            if (result == -1) {
                                player2Points += 1
                                if (gameType != "singleplayer")
                                {
                                    player1Turn = true
                                }

                            }
                            updatePoints()
                        }
                    if (!isAnyMoveLeft(buttons,blockCount))
                        {
                        resetBoard(buttons,blockCount)
                        }
                }
                }
            }
        button_start.setOnClickListener{
             resetBoard(buttons,blockCount)
            }

        button_exit.setOnClickListener{
            resetPoints()
            resetBoard(buttons,blockCount)
            finish()
            }

        }
    }
}

