package com.hashconcepts.wallpaperhd4k.ui.category

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.data.models.Category
import com.hashconcepts.wallpaperhd4k.databinding.CategoryFragmentBinding

class CategoryFragment : Fragment() {
    private lateinit var binding: CategoryFragmentBinding
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = CategoryFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRV()
    }

    private fun getCategories(): MutableList<Category> {
        return mutableListOf(
            Category(R.drawable.nature_category, "Nature"),
            Category(R.drawable.people_category, "People"),
            Category(R.drawable.tech_category, "Technology"),
            Category(R.drawable.photo_category, "Photography"),
            Category(R.drawable.popular_category, "Popular"),
            Category(R.drawable.food_category, "Food"),
            Category(R.drawable.background_category, "Backgrounds"),
            Category(R.drawable.art_category, "Art"),
            Category(R.drawable.animals_category, "Animals"),
            Category(R.drawable.plants_category, "Plants"),
            Category(R.drawable.cars_category, "Cars"),
            Category(R.drawable.halloween_category, "Halloween"),
        )
    }

    private fun setupRV() {
        categoryAdapter = CategoryAdapter(getCategories())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = categoryAdapter
        }
        categoryAdapter.setOnItemClickListener { categoryName ->
            val action = CategoryFragmentDirections.actionCategoryFragmentToListFragment(categoryName)
            findNavController().navigate(action)
        }
    }
}