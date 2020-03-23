package com.karol.simplegallery.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karol.simplegallery.R
import com.karol.simplegallery.helper.GlideApp
import kotlinx.android.synthetic.main.item_gallery_image.view.*

class GalleryImageAdapter(private val itemList: ArrayList<Image>) : RecyclerView.Adapter<GalleryImageAdapter.ViewHolder>() {

    private var context: Context? = null
    var listener: GalleryImageClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryImageAdapter.ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery_image, parent,
            false) //Instantiates a layout XML file into its corresponding View objects
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: GalleryImageAdapter.ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind() {

            val image = itemList[adapterPosition]

            // load image
            GlideApp.with(context!!)
                .load(image.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.ivGalleryImage)

            // adding click or tap handler for our image layout
            itemView.container.setOnClickListener {
                listener?.onClick(adapterPosition)
            }
        }
    }
}