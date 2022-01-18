package com.hashconcepts.wallpaperhd4k.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.hashconcepts.wallpaperhd4k.databinding.HomeFragmentBinding
import com.hashconcepts.wallpaperhd4k.ui.home.adapter.WallpaperLoadStateAdapter
import com.hashconcepts.wallpaperhd4k.ui.home.adapter.WallpaperPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding
    private lateinit var wallpaperPagingAdapter: WallpaperPagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        setupRV()
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.wallPapers.collectLatest { pagedData ->
                wallpaperPagingAdapter.submitData(pagedData)
            }
        }
    }

    private fun setupRV() {
        wallpaperPagingAdapter = WallpaperPagingAdapter()
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 2)
            (layoutManager as GridLayoutManager).spanSizeLookup =  object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    return if (position == adapter?.itemCount  && wallpaperPagingAdapter.itemCount > 0) 2 else 1
                }
            }
            adapter = wallpaperPagingAdapter.withLoadStateHeaderAndFooter(
                header = WallpaperLoadStateAdapter { wallpaperPagingAdapter.retry() },
                footer = WallpaperLoadStateAdapter { wallpaperPagingAdapter.retry() }
            )
            setHasFixedSize(true)
        }
        wallpaperPagingAdapter.setOnItemClickListener { photoID ->
            Snackbar.make(binding.root, photoID.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

}