package com.karol.simplegallery.helper

import java.io.File
class ImageFinder {

    fun getPicturePaths(root: File): ArrayList<File>{
        val fileList : ArrayList<File> = ArrayList()
        if (root.exists()) {
            root.walk().forEach{
                if((it.name.endsWith(".jpg", ignoreCase = true) or it.name.endsWith(".png", ignoreCase = true))
                    and
                    !(it.absolutePath.contains(".thumbnails", ignoreCase = true) or it.absolutePath.contains("cache",ignoreCase = true) )) {
                    fileList.add(it)
                }
            }
        }
        return fileList
    }

}