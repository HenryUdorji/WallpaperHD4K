package com.hashconcepts.wallpaperhd4k.ui.favourite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.databinding.ItemRvCategoryBinding
import com.hashconcepts.wallpaperhd4k.databinding.ItemRvWallpaperBinding
import com.squareup.picasso.Picasso

/**
 * @created 08/05/2022 - 9:04 PM
 * @project Wallpaper HD4K
 * @author  ifechukwu.udorji
 */
class FavouriteAdapter(
    private val favouriteWallpapers: MutableList<Photo>
): RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    inner class FavouriteViewHolder(val binding: ItemRvWallpaperBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            with(binding.image) {
                Picasso.get().load(photo.src.portrait).into(this)
                this.transitionName = photo.id.toString()

                setOnClickListener {
                    onItemClickListener?.let {
                        it(photo)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        val binding = ItemRvWallpaperBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavouriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val photo = favouriteWallpapers[position]
        holder.bind(photo)
    }

    override fun getItemCount(): Int {
        return favouriteWallpapers.size
    }

    private var onItemClickListener: ((photo: Photo) -> Unit) ? = null

    fun setOnItemClickListener(listener: (photo: Photo) -> Unit) {
        onItemClickListener = listener
    }
}