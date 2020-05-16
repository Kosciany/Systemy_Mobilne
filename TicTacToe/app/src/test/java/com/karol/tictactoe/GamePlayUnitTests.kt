package com.karol.tictactoe

import org.junit.Test

import org.junit.Assert.*

class GamePlayUnitTests {
    @Test
    fun testCheckWinnerHorizontal()
    {
        val blockCount = 10
        val winningCombo = 5


        val testArray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (j==2 && i >= 1 && i <= 4) {
                    testArray[i][j] = 1
                }
                else testArray[i][j] = 0
            }
        }

        val gamePlay = GamePlay()
        assertEquals(0, gamePlay.checkWinner(testArray,blockCount,winningCombo))

        testArray[5][2] = 1;
        assertEquals(1, gamePlay.checkWinner(testArray,blockCount,winningCombo))

        testArray[6][2] = 1;
        assertEquals(1, gamePlay.checkWinner(testArray,blockCount,winningCombo))

    }

    @Test
    fun testCheckWinnerVertical()
    {
        val blockCount = 10
        val winningCombo = 5


        val testArray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (i==2 && j >= 1 && j <= 4) {
                    testArray[i][j] = -1
                }
                else testArray[i][j] = 0
            }
        }

        val gamePlay = GamePlay()
        assertEquals(0, gamePlay.checkWinner(testArray,blockCount,winningCombo))

        testArray[2][5] = -1;
        assertEquals(-1, gamePlay.checkWinner(testArray,blockCount,winningCombo))

        testArray[2][6] = -1;
        assertEquals(-1, gamePlay.checkWinner(testArray,blockCount,winningCombo))
    }


    @Test
    fun testCheckWinnerDiagonal(){
        val blockCount = 10
        val winningCombo = 5


        val testArray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (i>= 1 && i <= 4  && j >= 1 && j <= 4) {
                    testArray[i][j] = -1
                }
                else testArray[i][j] = 0
            }
        }

        val gamePlay = GamePlay()
        assertEquals(0, gamePlay.checkWinner(testArray,blockCount,winningCombo))

        testArray[5][5] = -1;
        assertEquals(-1, gamePlay.checkWinner(testArray,blockCount,winningCombo))

        testArray[6][6] = -1;
        assertEquals(-1, gamePlay.checkWinner(testArray,blockCount,winningCombo))
    }

    @Test
    fun checkMovesLeftOnUnFullBoard()
    {
        val blockCount = 10
        val winningCombo = 5


        val testArray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (i>= 1 && i <= 4  && j >= 1 && j <= 4) {
                    testArray[i][j] = -1
                }
                else testArray[i][j] = 0
            }
        }
        val gamePlay = GamePlay()
        assertEquals(true,gamePlay.isAnyMoveLeft(testArray,blockCount))
    }

    @Test
    fun checkMovesLeftOnFullBoard()
    {
        val blockCount = 10
        val testArray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                testArray[i][j] = 1
            }
        }
        val gamePlay = GamePlay()
        assertEquals(false,gamePlay.isAnyMoveLeft(testArray,blockCount))
    }


}
