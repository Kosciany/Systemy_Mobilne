package com.karol.imagepuzzle

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.board.*
import kotlin.properties.Delegates
import kotlin.random.Random
import kotlin.random.nextUInt


class PuzzleBoard : AppCompatActivity() {

    private val piecesNumber = 12
    private val rows = 3
    private val cols = 4
    private var done = false
    private var start by Delegates.notNull<Long>()
    private var end by Delegates.notNull<Long>()
    private lateinit var pieces : ArrayList<PuzzlePiece>
    private lateinit var piecesPositions : ArrayList<IntArray>
    private var helpOn = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.board)
        hideActionBar()

        val imageID = intent.getIntExtra("id",1)
        val options = BitmapFactory.Options()
        options.inScaled = false
        val bImage = BitmapFactory.decodeResource(this.resources, imageID, options)
        puzzleBoard.setImageBitmap(bImage)
        helpButton.visibility = View.GONE

        startButton.setOnClickListener {
            startButton.visibility = View.GONE
            if (!done)
            {
                done = !done
                helpButton.visibility = View.VISIBLE
                val drawable = puzzleBoard.drawable as BitmapDrawable
                val bitmap = drawable.bitmap
                val f = FloatArray (9)
                puzzleBoard.imageMatrix.getValues(f)
                puzzleBoard.alpha = 0.1F
                val offsetX = f[Matrix.MTRANS_X]
                val offsetY = f[Matrix.MTRANS_Y]

                val layout = findViewById<RelativeLayout>(R.id.puzzlePile)

                pieces = splitImage(bitmap,puzzleBoard.height/rows)
                piecesPositions = generateRandomPositions(piecesNumber,
                    puzzlePile.left + puzzleBoard.height/rows,
                    (puzzlePile.right - 2*puzzleBoard.height/rows),
                    puzzleBoard.bottom + puzzleBoard.height/rows,
                    (puzzlePile.bottom - 2*puzzleBoard.height/rows),
                    10)
                val touchListener = PuzzleTouchListener(this)
                for ((i, piece) in pieces.withIndex()) {

                    piece.setOnTouchListener(touchListener)
                    layout.addView(piece)
                    piece.xOffset = offsetX
                    piece.yOffset = offsetY
                    piece.adjustViewBounds = true
                    piece.applyStartPosition(piecesPositions, i)
                    piece.left = piece.xStart
                    piece.top = piece.yStart
                    val lParams = piece.layoutParams as RelativeLayout.LayoutParams
                    lParams.leftMargin = piece.xStart
                    lParams.topMargin = piece.yStart
                    piece.layoutParams = lParams
                    piece.drawable.setBounds(
                        0,
                        0,
                        puzzleBoard.width / cols,
                        puzzleBoard.height / rows
                    )


                }
                val bImage = BitmapFactory.decodeResource(this.resources, R.drawable.puzzletemplate)
                puzzleBoard.setImageBitmap(bImage)
                puzzleBoard.alpha = 0.9F
                start = System.currentTimeMillis()
            }
        }

        exitButton.setOnClickListener(){
           finish()
        }
        helpButton.setOnClickListener(){

            if (helpOn)
                {
                    val helpIm = BitmapFactory.decodeResource(this.resources, R.drawable.puzzletemplate)
                    puzzleBoard.setImageBitmap(bImage)
                    puzzleBoard.alpha = 0.9F
                    puzzleBoard.setImageBitmap(helpIm)
                }
            else
                {
                val helpIm = BitmapFactory.decodeResource(this.resources, imageID, options)
                puzzleBoard.setImageBitmap(bImage)
                puzzleBoard.alpha = 0.3F
                puzzleBoard.setImageBitmap(helpIm)
                }
            helpOn = !helpOn

        }
    }


    fun isPuzzleSolved() : Boolean
    {
        for (i in 0 until piecesNumber)
        {
            if (pieces[i].canMove) {
                return false
            }
        }
        end = System.currentTimeMillis()
        popupText()
        //SystemClock.sleep(5000)
        //finish()
        return true
    }

    private fun popupText() {
            timePopup.setText("""Congratulations! It took ${(end - start) / 1000} seconds """)
            timePopup.append("Press exit to return to the menu")
            timePopup.visibility = View.VISIBLE
        }


    private fun hideActionBar() {
        supportActionBar?.hide()
    }

    private fun generateRandomPositions(piecesNumber: Int, xMin: Int , xMax: Int, yMin: Int, yMax: Int, theta: Int): ArrayList<IntArray> {
        var piecesPositions : ArrayList<IntArray> = ArrayList(piecesNumber)
        val piecesXPositions = IntArray(piecesNumber) {-1}
        val piecesYPositions = IntArray(piecesNumber) {-1}
        for (i in 0 until piecesNumber)
        {
            while (true)
            {
                val x = Random.nextUInt(xMin.toUInt(), xMax.toUInt())
                val y = Random.nextUInt(yMin.toUInt(), yMax.toUInt())
                var uniqueCoordinates = true
                for (j in 0 until piecesNumber)
                {
                    if (piecesXPositions[j] == -1 && piecesYPositions[j] == -1)
                    {
                        continue
                    }
                    if ( (x <= (piecesXPositions[j].toUInt() + theta.toUInt())
                                &&
                                x >= (piecesXPositions[j].toUInt() - theta.toUInt()))
                        &&
                        (y <= (piecesYPositions[j].toUInt() + theta.toUInt())
                                &&
                                y >= (piecesYPositions[j].toUInt() - theta.toUInt())))
                    {
                        uniqueCoordinates = false
                    }
                }
                if (uniqueCoordinates)
                {
                    piecesXPositions[i] = x.toInt()
                    piecesYPositions[i] = y.toInt()
                    break;
                }
            }
            piecesPositions.add(intArrayOf(piecesXPositions[i],piecesYPositions[i]))
        }
        return piecesPositions
    }


    private fun splitImage(bitmap: Bitmap, puzzleSquareSize: Int): ArrayList<PuzzlePiece> {


        val pieces: ArrayList<PuzzlePiece> = ArrayList(piecesNumber)

        // Get the bitmap of the source image

        // Calculate the with and height of the pieces
        val pieceWidth = (bitmap.width / cols)
        val pieceHeight = (bitmap.height / rows)

        // Create each bitmap piece and add it to the resulting array
        var yCoord = 0
        for (row in 0 until rows) {
            var xCoord = 0
            for (col in 0 until cols) {

                val bitmapPart = Bitmap.createBitmap(bitmap, xCoord, yCoord, pieceWidth, pieceHeight)
                val scaledBitmapPart = Bitmap.createScaledBitmap(bitmapPart,puzzleSquareSize,puzzleSquareSize,true)
                val puzzlePiece = PuzzlePiece(this)

                puzzlePiece.setImageDrawable(BitmapDrawable(scaledBitmapPart))

                puzzlePiece.xPos = col
                puzzlePiece.yPos = row
                puzzlePiece.size = puzzleSquareSize
                pieces.add(puzzlePiece)
                xCoord += pieceWidth
            }
            yCoord += pieceHeight
        }
        return pieces
    }
}

