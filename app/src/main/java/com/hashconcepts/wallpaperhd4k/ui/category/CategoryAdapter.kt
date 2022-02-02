package com.hashconcepts.wallpaperhd4k.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hashconcepts.wallpaperhd4k.data.models.Category
import com.hashconcepts.wallpaperhd4k.databinding.ItemRvCategoryBinding

class CategoryAdapter(
    private val categories: MutableList<Category>
): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    inner class CategoryViewHolder(val binding: ItemRvCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: Category) {
            binding.apply {
                image.setImageResource(category.imageResID)
                categoryName.text = category.name
                root.setOnClickListener {
                    onItemClickListener?.let { it(category.name) }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemRvCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    private var onItemClickListener: ((categoryName: String) -> Unit) ? = null

    fun setOnItemClickListener(listener: (categoryName: String) -> Unit) {
        onItemClickListener = listener
    }
}
