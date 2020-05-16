package com.karol.tictactoe

import org.junit.Test

import org.junit.Assert.*
import com.karol.tictactoe.GamePlay

class GamePlayUnitTests {
    @Test
    fun testCheckWinnerHorizontal()
    {
        val blockCount = 10
        val winningCombo = 5


        val testarray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (j==2 && i >= 1 && i <= 4) {
                    testarray[i][j] = 1
                }
                else testarray[i][j] = 0
            }
        }

        val gameplay = com.karol.tictactoe.GamePlay()
        assertEquals(0, gameplay.checkWinner(testarray,blockCount,winningCombo))

        testarray[5][2] = 1;
        assertEquals(1, gameplay.checkWinner(testarray,blockCount,winningCombo))

        testarray[6][2] = 1;
        assertEquals(1, gameplay.checkWinner(testarray,blockCount,winningCombo))

    }

    @Test
    fun testCheckWinnerVertical()
    {
        val blockCount = 10
        val winningCombo = 5


        val testarray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (i==2 && j >= 1 && j <= 4) {
                    testarray[i][j] = -1
                }
                else testarray[i][j] = 0
            }
        }

        val gameplay = com.karol.tictactoe.GamePlay()
        assertEquals(0, gameplay.checkWinner(testarray,blockCount,winningCombo))

        testarray[2][5] = -1;
        assertEquals(-1, gameplay.checkWinner(testarray,blockCount,winningCombo))

        testarray[2][6] = -1;
        assertEquals(-1, gameplay.checkWinner(testarray,blockCount,winningCombo))
    }


    @Test
    fun testCheckWinnerDiagonal(){
        val blockCount = 10
        val winningCombo = 5


        val testarray = Array(blockCount) { IntArray(blockCount)}
        for (i in 0 until blockCount)
        {
            for (j in 0 until blockCount)
            {
                if (i>= 1 && i <= 4  && j >= 1 && j <= 4) {
                    testarray[i][j] = -1
                }
                else testarray[i][j] = 0
            }
        }

        val gameplay = com.karol.tictactoe.GamePlay()
        assertEquals(0, gameplay.checkWinner(testarray,blockCount,winningCombo))

        testarray[5][5] = -1;
        assertEquals(-1, gameplay.checkWinner(testarray,blockCount,winningCombo))

        testarray[6][6] = -1;
        assertEquals(-1, gameplay.checkWinner(testarray,blockCount,winningCombo))
    }


}
