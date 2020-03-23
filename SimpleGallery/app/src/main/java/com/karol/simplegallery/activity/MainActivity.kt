package com.karol.simplegallery.activity


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import com.karol.simplegallery.R
import com.karol.simplegallery.adapter.Image
import com.karol.simplegallery.adapter.GalleryImageAdapter
import com.karol.simplegallery.adapter.GalleryImageClickListener
import com.karol.simplegallery.fragment.GalleryFullscreenFragment
import com.karol.simplegallery.helper.ImageFinder
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), GalleryImageClickListener {

    private val COLUMNS: Int = 2
    private var MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: Int = 0
    private val imageList = ArrayList<Image>()
    lateinit var galleryAdapter: GalleryImageAdapter


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (
            (Build.VERSION.SDK_INT <= 23
                    ||
             (ContextCompat.checkSelfPermission(this,
                 Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED))
           )
        {
            //permission granted
        }
        else
        {
            //demand permission
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
        }

        galleryAdapter = GalleryImageAdapter(imageList)
        galleryAdapter.listener = this


        // init recyclerview
        recyclerView.layoutManager = GridLayoutManager(this, COLUMNS)
        recyclerView.adapter = galleryAdapter

        // load images
        loadImages()
    }

    private fun loadImages() {
        val storagePath:String = Environment.getExternalStorageDirectory().absolutePath
        val finder = ImageFinder()
        val fileArray = finder.getPicturePaths(File(storagePath))

        for(i in 0 until fileArray.size)
        {
            imageList.add(Image(fileArray[i]))
        }

        galleryAdapter.notifyDataSetChanged()
    }


    override fun onClick(position: Int) {
        // handle click of image

        val bundle = Bundle()
        bundle.putSerializable("images", imageList)
        bundle.putInt("position", position)

        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val galleryFragment = GalleryFullscreenFragment()
        galleryFragment.arguments = bundle
        galleryFragment.show(fragmentTransaction, "gallery")
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray)
    {
        when (requestCode)
        {
            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE ->
            {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay!
                }
                else
                {
                    val builder: android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
                    builder.setMessage("No permission to read from internal Storage. " +
                                        "App will be closed. Go to App Permission and " +
                                        "grant storage permission to this app if you want to use it.")
                        .setCancelable(false)
                        .setPositiveButton("OK")
                        { dialog, _ ->
                            dialog.cancel()
                            moveTaskToBack(true)
                            exitProcess(-1)
                        }
                    builder.create()?.show()
                }
                return
            }

        }
    }
}

