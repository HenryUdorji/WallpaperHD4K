package com.hashconcepts.wallpaperhd4k.ui.favourite

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.hashconcepts.wallpaperhd4k.R
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.databinding.FavouriteFragmentBinding
import com.hashconcepts.wallpaperhd4k.extentions.showErrorSnackbar
import com.hashconcepts.wallpaperhd4k.ui.category.CategoryFragmentDirections
import com.hashconcepts.wallpaperhd4k.utils.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment : Fragment() {
    private lateinit var binding: FavouriteFragmentBinding
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FavouriteFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

        viewModel.wallpaperLiveData.observe(viewLifecycleOwner) { state ->
            when(state) {
                is Resource.Loading -> binding.progressbar.isVisible = true
                is Resource.Error -> binding.root.showErrorSnackbar(state.message!!)
                is Resource.Success -> setupRv(state.data!! as MutableList<Photo>)
            }
        }
    }

    private fun setupRv(favouriteWallpapers: MutableList<Photo>) {
        binding.progressbar.isVisible = false

        favouriteAdapter = FavouriteAdapter(favouriteWallpapers)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = favouriteAdapter
        }
        favouriteAdapter.setOnItemClickListener { photo ->
            val action = FavouriteFragmentDirections.actionFavouriteFragmentToDetailsFragment(photo)
            findNavController().navigate(action)
        }
    }

}