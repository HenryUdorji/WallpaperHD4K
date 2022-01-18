package com.hashconcepts.wallpaperhd4k.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.DifferCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.databinding.ItemRvWallpaperBinding

class WallpaperRvAdapter() : ListAdapter<Photo, WallpaperRvAdapter.WallpaperViewholder>(DifferCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WallpaperViewholder {
        val binding = ItemRvWallpaperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return WallpaperViewholder(binding)
    }

    override fun onBindViewHolder(holder: WallpaperViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    private class DifferCallback : DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }
    }

    inner class WallpaperViewholder(val binding: ItemRvWallpaperBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(photo: Photo) {

        }
    }

    private var onItemClickListener: ((photoID: Int) -> Unit) ? = null

    fun setOnItemClickListener(listener: (photoID: Int) -> Unit) {
        onItemClickListener = listener
    }
}

