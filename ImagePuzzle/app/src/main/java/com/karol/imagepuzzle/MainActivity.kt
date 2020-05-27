package com.karol.imagepuzzle

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity() {

   private lateinit var pieces : ArrayList<PuzzlePiece>

    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hideActionBar()

        startButton.setOnClickListener() {
            startButton.visibility = View.GONE
            val layout = findViewById<RelativeLayout>(R.id.puzzlePile)

            pieces = splitImage()
            val touchListener = PuzzleTouchListener()
            for(piece in  pieces){
                piece.setOnTouchListener(touchListener)
                layout.addView(piece)
            }

        exitButton.setOnClickListener(){
            exitProcess(0)
        }
        }


    }
    private fun splitImage(): ArrayList<PuzzlePiece> {
        val piecesNumber = 12
        val rows = 4
        val cols = 3
        val puzzleBoard = findViewById<ImageView>(R.id.puzzleBoard)
        val f = FloatArray (9)
        puzzleBoard.imageMatrix.getValues(f)
        puzzleBoard.alpha = 0.1F
        val offsetX = f[Matrix.MTRANS_X]
        val offsetY = f[Matrix.MTRANS_Y]
        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        val pieces: ArrayList<PuzzlePiece> = ArrayList(piecesNumber)

        // Get the bitmap of the source image
        val drawable = puzzleBoard.drawable as BitmapDrawable
        val bitmap = drawable.bitmap

        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, (bitmap.width * scaleX).toInt(), (bitmap.height * scaleY).toInt(),true )


        // Calculate the with and height of the pieces
        val pieceWidth = (scaledBitmap.width / cols)
        val pieceHeight = (scaledBitmap.height / rows)

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                val bitmapPart = Bitmap.createBitmap(scaledBitmap, xCoord, yCoord, pieceWidth, pieceHeight)
                val puzzlePiece = PuzzlePiece(this)
                puzzlePiece.setImageBitmap(bitmapPart)
                puzzlePiece.xPos = col
                puzzlePiece.yPos = row
                puzzlePiece.xOffset = offsetX.toInt()
                puzzlePiece.yOffset = offsetY.toInt()
                puzzlePiece.pieceWidth = pieceWidth
                puzzlePiece.pieceHeight = pieceHeight
                pieces.add(puzzlePiece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }
}
