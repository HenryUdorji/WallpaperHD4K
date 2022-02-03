package com.hashconcepts.wallpaperhd4k.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.hashconcepts.wallpaperhd4k.databinding.ListFragmentBinding
import com.hashconcepts.wallpaperhd4k.ui.home.adapter.WallpaperLoadStateAdapter
import com.hashconcepts.wallpaperhd4k.ui.home.adapter.WallpaperPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var viewModel: ListViewModel
    private lateinit var binding: ListFragmentBinding
    private lateinit var wallpaperPagingAdapter: WallpaperPagingAdapter
    private val args: ListFragmentArgs by navArgs()
    private lateinit var category: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[ListViewModel::class.java]

        category = args.categoryName

        binding.buttonBack.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.appBarTitle.text = category

        setupRV()
        fetchData()
    }

    private fun fetchData() {
        lifecycleScope.launch {
            viewModel.wallPapers(category).collectLatest { pagedData ->
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
        wallpaperPagingAdapter.setOnItemClickListener { photo, imageView ->
            val extras = FragmentNavigatorExtras(imageView to photo.id.toString())
            val action = ListFragmentDirections.actionListFragmentToDetailsFragment(photo)
            findNavController().navigate(action, extras)
        }
        binding.recyclerView.post { startPostponedEnterTransition() }
    }

}