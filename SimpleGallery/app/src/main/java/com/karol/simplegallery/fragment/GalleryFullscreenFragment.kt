package com.karol.simplegallery.fragment


import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.karol.simplegallery.R
import com.karol.simplegallery.adapter.Image
import com.karol.simplegallery.helper.GlideApp
import com.karol.simplegallery.helper.ZoomOutPageTransformer
import kotlinx.android.synthetic.main.image_fullscreen.view.*

class GalleryFullscreenFragment : DialogFragment() {

    private var imageList = ArrayList<Image>()
    private var selectedPosition: Int = 0

    lateinit var viewPager: ViewPager

    lateinit var galleryPagerAdapter: GalleryPagerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_gallery_fullscreen, container, false)

        viewPager = view.findViewById(R.id.viewPager)

        galleryPagerAdapter = GalleryPagerAdapter()

        imageList = arguments?.getSerializable("images") as ArrayList<Image>
        selectedPosition = arguments!!.getInt("position")

        viewPager.adapter = galleryPagerAdapter
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener)
        viewPager.setPageTransformer(true, ZoomOutPageTransformer())

        setCurrentItem(selectedPosition)

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
    }

    private fun setCurrentItem(position: Int) {
        viewPager.setCurrentItem(position, false)
    }

    // viewpager page change listener
    internal var viewPagerPageChangeListener: ViewPager.OnPageChangeListener =
        object : ViewPager.OnPageChangeListener {

            override fun onPageSelected(position: Int) {
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
            }

            override fun onPageScrollStateChanged(arg0: Int) {
            }
        }

    // gallery adapter
    inner class GalleryPagerAdapter : PagerAdapter() {

        override fun instantiateItem(container: ViewGroup, position: Int): Any {

            val layoutInflater = activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val view = layoutInflater.inflate(R.layout.image_fullscreen, container, false)

            val image = imageList.get(position)

            // load image
            GlideApp.with(context!!)
                .load(image.imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view.ivFullscreenImage)

            container.addView(view)

            return view
        }

        override fun getCount(): Int {
            return imageList.size
        }

        override fun isViewFromObject(view: View, obj: Any): Boolean {
            return view === obj as View
        }

        override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
            container.removeView(obj as View)
        }
    }
}