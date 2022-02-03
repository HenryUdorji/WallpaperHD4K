package com.hashconcepts.wallpaperhd4k.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hashconcepts.wallpaperhd4k.data.remote.ServiceApi
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperCategoryDataSource
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperDataSource
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperRepository
import com.hashconcepts.wallpaperhd4k.utils.Constants
import com.hashconcepts.wallpaperhd4k.utils.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val serviceApi: ServiceApi,
    private val repository: WallpaperRepository,
    private val networkManager: NetworkManager
) : ViewModel() {

    val networkObserver = networkManager.observeConnectionStatus


    fun wallPapers(category: String) = Pager(config = PagingConfig(30), pagingSourceFactory = {
        WallpaperCategoryDataSource(serviceApi, category)
    }).flow.cachedIn(viewModelScope)


}