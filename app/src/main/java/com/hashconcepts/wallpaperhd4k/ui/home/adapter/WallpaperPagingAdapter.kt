package com.hashconcepts.wallpaperhd4k.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.databinding.ItemRvWallpaperBinding
import com.squareup.picasso.Picasso

class WallpaperPagingAdapter : PagingDataAdapter<Photo, WallpaperPagingAdapter.WallpaperViewHolder>(
    DifferCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewHolder {
        val binding = ItemRvWallpaperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WallpaperViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    private class DifferCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    inner class WallpaperViewHolder(private val binding: ItemRvWallpaperBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {
            Picasso.get().load(photo.src.portrait).placeholder(R.color.color_accent).into(binding.image)

            binding.root.setOnClickListener {
                onItemClickListener?.let { it(photo.id) }
            }
        }
    }

    private var onItemClickListener: ((photoID: Int) -> Unit) ? = null

    fun setOnItemClickListener(listener: (photoID: Int) -> Unit) {
        onItemClickListener = listener
    }
}

