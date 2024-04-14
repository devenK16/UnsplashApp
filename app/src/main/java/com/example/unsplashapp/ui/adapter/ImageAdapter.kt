package com.example.unsplashapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplashapp.R
import com.example.unsplashapp.models.Image

class ImageAdapter : androidx.recyclerview.widget.ListAdapter<Image, ImageAdapter.ImageViewHolder>(
    ComparatorDiffUtil()
) {
    private val images = mutableListOf<Image>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
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

    inner class ImageViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Image) {
            itemView.findViewById<ImageView>(R.id.imageView).apply {
                Glide.with(this)
                    .load(image.urls.regular)
                    .into(this)
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