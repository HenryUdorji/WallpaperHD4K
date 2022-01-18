package com.hashconcepts.wallpaperhd4k.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.databinding.ItemRvWallpaperBinding
import com.makeramen.roundedimageview.RoundedImageView
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
            with(binding.image) {
                Picasso.get().load(photo.src.portrait).into(this)
                this.transitionName = photo.id.toString()

                this.setOnClickListener {
                    onItemClickListener?.let { it(photo, this) }
                }
            }
        }
    }

    private var onItemClickListener: ((photo: Photo, imageView: RoundedImageView) -> Unit) ? = null

    fun setOnItemClickListener(listener: (photo: Photo, imageView: RoundedImageView) -> Unit) {
        onItemClickListener = listener
    }
}

