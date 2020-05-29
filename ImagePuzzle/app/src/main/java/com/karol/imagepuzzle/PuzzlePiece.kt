package com.karol.imagepuzzle

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.widget.RelativeLayout

class PuzzlePiece(context: Context?) : AppCompatImageView(context) {
    fun applyStartPosition(piecesPositions: ArrayList<IntArray>, i: Int) {
        xStart = piecesPositions[i][0]
        yStart = piecesPositions[i][1]
    }

    var xPos = 0
    var yPos = 0
    var xOffset = 0F
    var yOffset = 0F
    var xStart = 0
    var yStart = 0
    var size = 0
    var canMove = true
}