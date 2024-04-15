package com.example.unsplashapp.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

class ImageLoader(private val context: Context) {
    private val memoryCache: MutableMap<String, Bitmap> = LinkedHashMap(100, 0.75f, true)
    private val diskCacheDir: File = File(context.cacheDir, "images")

    init {
        if (!diskCacheDir.exists()) diskCacheDir.mkdir()
    }

    suspend fun loadBitmap(imageUrl: String, imageView: ImageView) {
        val bitmap = getBitmapFromCache(imageUrl) ?: loadBitmapFromNetwork(imageUrl)
        bitmap?.let {
            imageView.setImageBitmap(it)
        }
    }

    private fun getBitmapFromCache(imageUrl: String): Bitmap? =
        synchronized(memoryCache) {
            memoryCache[imageUrl] ?: getBitmapFromDiskCache(imageUrl)
        }

    private fun getBitmapFromDiskCache(imageUrl: String): Bitmap? {
        val fileName = imageUrl.hashCode().toString()
        val file = File(diskCacheDir, fileName)
        return if (file.exists()) BitmapFactory.decodeFile(file.path) else null
    }

    private suspend fun loadBitmapFromNetwork(imageUrl: String): Bitmap? = withContext(Dispatchers.IO) {
        try {
            val connection = URL(imageUrl).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val inputStream = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(inputStream)
            inputStream.close()
            connection.disconnect()

            bitmap?.let {
                putBitmapInCache(imageUrl, it)
                saveBitmapToDiskCache(imageUrl, it)
            }
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun putBitmapInCache(imageUrl: String, bitmap: Bitmap) {
        synchronized(memoryCache) {
            if (memoryCache[imageUrl] == null) {
                memoryCache[imageUrl] = bitmap
            }
        }
    }

    private fun saveBitmapToDiskCache(imageUrl: String, bitmap: Bitmap) {
        val fileName = imageUrl.hashCode().toString()
        val file = File(diskCacheDir, fileName)
        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 85, out)
        }
    }
}