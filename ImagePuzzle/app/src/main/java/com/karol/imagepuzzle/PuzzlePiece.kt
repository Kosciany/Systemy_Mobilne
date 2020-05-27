package com.karol.imagepuzzle

import android.content.Context
import android.support.v7.widget.AppCompatImageView

class PuzzlePiece(context: Context?) : AppCompatImageView(context) {
    var xPos = 0
    var yPos = 0
    var xOffset = 0
    var yOffset = 0
    var pieceWidth = 0
    var pieceHeight = 0
    var canMove = true
}