package com.karol.imagepuzzle

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView


class ImageAdapter(c:Context, imagesIDArray: ArrayList<Int>, imageDim: Int) : BaseAdapter() {
    private val context = c
    private val idArray = imagesIDArray
    private val imageDim = imageDim
    private val padding = 5

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(context);
            imageView.layoutParams = ViewGroup.LayoutParams(imageDim, imageDim);
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP;
            imageView.setPadding(padding, padding, padding, padding);
        }
        else
        {
            imageView = convertView as ImageView;
        }
        imageView.setImageResource(idArray[position]);
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
