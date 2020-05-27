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


class MainActivity : AppCompatActivity() {

   private lateinit var pieces : ArrayList<Bitmap>

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
                val imagePiece = ImageView(applicationContext)
                imagePiece.setImageBitmap(piece)
                imagePiece.setOnTouchListener(touchListener)
                layout.addView(imagePiece)
            }

        }


    }
    private fun splitImage(): ArrayList<Bitmap> {
        val piecesNumber = 12
        val rows = 4
        val cols = 3
        val puzzleBoard = findViewById<ImageView>(R.id.puzzleBoard)
        val f = FloatArray (9)
        puzzleBoard.imageMatrix.getValues(f)
        puzzleBoard.alpha = 0.1F

        val scaleX = f[Matrix.MSCALE_X]
        val scaleY = f[Matrix.MSCALE_Y]

        val pieces: ArrayList<Bitmap> = ArrayList(piecesNumber)

        // Get the bitmap of the source image
        val drawable = puzzleBoard.drawable as BitmapDrawable
        val bitmap = drawable.bitmap

        // Calculate the with and height of the pieces
        val pieceWidth = (bitmap.width / cols)
        val pieceHeight = (bitmap.height / rows)

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {
                val bitmapPart = Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight)
                val scaledBitmap = Bitmap.createScaledBitmap(bitmapPart, (pieceWidth * scaleX).toInt(), (pieceHeight * scaleY).toInt(),true )

                pieces.add(scaledBitmap)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }
}
