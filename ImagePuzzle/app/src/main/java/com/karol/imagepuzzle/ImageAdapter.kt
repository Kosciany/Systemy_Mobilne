package com.karol.imagepuzzle

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.BaseAdapter
import android.widget.ImageView


class ImageAdapter(c:Context, imagesIDArray: ArrayList<Int>, columnsNum: Int) : BaseAdapter() {
    private val context = c
    private val idArray = imagesIDArray
    private val columns = columnsNum
    private val padding = 3

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val imageView: ImageView = ImageView(context)
        imageView.setImageResource(idArray[position])
        if (parent != null) {
                imageView.layoutParams = AbsListView.LayoutParams((0.9*parent.width/columns).toInt(), (0.9*parent.width/columns).toInt())
            }
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.setPadding(padding, padding, padding, padding)
        return imageView
    }

    override fun getItem(position: Int): Any? {
        return null;
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return idArray.lastIndex
    }

}