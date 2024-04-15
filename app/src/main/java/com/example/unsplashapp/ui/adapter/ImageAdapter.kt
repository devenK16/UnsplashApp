package com.example.unsplashapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashapp.R
import com.example.unsplashapp.models.Image
import com.example.unsplashapp.util.ImageLoader
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ImageAdapter(private val context: Context) : androidx.recyclerview.widget.ListAdapter<Image, ImageAdapter.ImageViewHolder>(
    ComparatorDiffUtil()
) {
    private val imageLoader = ImageLoader(context)
    private val images = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view,imageLoader)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(images[position])
    }

    override fun getItemCount() = images.size

    fun addImages(newImages: List<Image>) {
        val currentSize = images.size
        images.addAll(newImages)
        notifyItemRangeInserted(currentSize, newImages.size)
    }

    fun clearImages() {
        val currentSize = images.size
        images.clear()
        notifyItemRangeRemoved(0, currentSize)
    }

    class ImageViewHolder(
        private val itemView: View,
        private val imageLoader: ImageLoader
    ) : RecyclerView.ViewHolder(itemView) {
        private var currentLoadJob: Job? = null

        fun bind(image: Image) {
            currentLoadJob?.cancel()
            val imageView = itemView.findViewById<ImageView>(R.id.imageView)
            imageView.setImageResource(R.drawable.placeholder_3)
            imageView.tag = image.urls.regular  // Set a unique tag

            currentLoadJob = CoroutineScope(Dispatchers.Main).launch {
                imageLoader.loadBitmap(image.urls.regular, imageView)
            }
        }
    }

    class ComparatorDiffUtil : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Image, newItem: Image): Boolean {
            return oldItem == newItem
        }
    }
}