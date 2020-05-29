package com.karol.imagepuzzle


import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.AdapterView
import android.widget.GridView


class MainActivity : AppCompatActivity() {

    private lateinit var imagesIDArray: ArrayList<Int>
    private val columnNum = 2
    private val imageFactor = 0.8

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imagesIDArray = getImagesIdentifiers()
        val gridView = findViewById<GridView>(R.id.gridImageView)
        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        val width: Int = size.x

        gridView.adapter = ImageAdapter(this,imagesIDArray,(imageFactor*width/columnNum).toInt())

        gridView.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, view, position, id -> // Send intent to SingleViewActivity
                val i = Intent(applicationContext, PuzzleBoard::class.java);
                // Pass image index
                i.putExtra("id", imagesIDArray[position])
                startActivity(i)

            }

    }

    private fun getImagesIdentifiers(): ArrayList<Int> {

        var resID: Int
        val images = ArrayList<Int>()
        var imgID = 1
        do {
            resID = resources.getIdentifier("image$imgID", "drawable", "com.karol.imagepuzzle");
            if (resID != 0) {
                imgID++
                images.add(resID);
            }
        } while (resID != 0);

        return images
    }
}


