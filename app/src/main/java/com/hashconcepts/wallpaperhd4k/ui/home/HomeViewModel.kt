package com.hashconcepts.wallpaperhd4k.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.hashconcepts.wallpaperhd4k.data.remote.ServiceApi
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperDataSource
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperRepository
import com.hashconcepts.wallpaperhd4k.utils.Constants
import com.hashconcepts.wallpaperhd4k.utils.NetworkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val serviceApi: ServiceApi,
    private val repository: WallpaperRepository,
    private val networkManager: NetworkManager
) : ViewModel() {

    val networkObserver = networkManager.observeConnectionStatus

    val wallPapers = Pager(config = PagingConfig(30), pagingSourceFactory = {
        WallpaperDataSource(serviceApi)
    }).flow.cachedIn(viewModelScope)


}