package com.karol.imagepuzzle

import org.junit.Test

import org.junit.Assert.*
import java.nio.file.Files.size

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class PuzzleUnitTest {
    @Test
    fun testPositionGeneration() {
        val puzzleBoard = PuzzleBoard()
        val positionArray : ArrayList<IntArray>
        val piecesNumber = 50
        val min = 0
        val max = 500
        val theta = 5
        positionArray = puzzleBoard.generateRandomPositions(piecesNumber,min,max,min,max,theta)
        for (position in positionArray)
            {
               assert(position[0]>min)
               assert(position[1]>min)
               assert(position[0]<max)
               assert(position[1]<max)
            }

    }


}


