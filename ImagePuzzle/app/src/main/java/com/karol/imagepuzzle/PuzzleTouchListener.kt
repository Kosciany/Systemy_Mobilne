package com.karol.imagepuzzle

import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.RelativeLayout
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sqrt



class PuzzleTouchListener(puzzleActivity: PuzzleBoard) : OnTouchListener {
    private var xDelta = 0f
    private var yDelta = 0f
    private lateinit var puzzleBoard: PuzzleBoard
    private var activity = puzzleActivity

    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
        val x = motionEvent.rawX
        val y = motionEvent.rawY
        val tolerance: Double = sqrt(
            view.width.toDouble().pow(2.0) + view.height.toDouble().pow(2.0)
        )
        val piece = view as PuzzlePiece
        if (!piece.canMove) {
            return true
        }
        val lParams =
            view.getLayoutParams() as RelativeLayout.LayoutParams
        when (motionEvent.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_DOWN -> {
                xDelta = x - lParams.leftMargin
                yDelta = y - lParams.topMargin
                piece.bringToFront()
            }
            MotionEvent.ACTION_MOVE -> {
                lParams.leftMargin = (x - xDelta).toInt()
                lParams.topMargin = (y - yDelta).toInt()
                view.setLayoutParams(lParams)
            }
            MotionEvent.ACTION_UP -> {
                val xDiff: Float = abs((piece.xPos * piece.width) - lParams.leftMargin).toFloat()
                val yDiff: Float = abs((piece.yPos * piece.height) - lParams.topMargin).toFloat()
                if (xDiff <= tolerance && yDiff <= tolerance) {
                    lParams.leftMargin = (piece.xPos * piece.size) + piece.xOffset.toInt()
                    lParams.topMargin = (piece.yPos * piece.size) + piece.yOffset.toInt()
                    piece.layoutParams = lParams
                    piece.canMove = false
                    activity.isPuzzleSolved()
                }
                else
                {
                    lParams.leftMargin = piece.xStart
                    lParams.topMargin = piece.yStart
                    piece.layoutParams = lParams
                    sendViewToBack(piece)
                }
            }
        }
        return true
    }

    private fun sendViewToBack(child: View) {
        val parent = child.parent as ViewGroup
        parent.removeView(child)
        parent.addView(child, 0)
    }
}