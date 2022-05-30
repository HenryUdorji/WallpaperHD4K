package com.hashconcepts.wallpaperhd4k.ui.favourite

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperRepository
import com.hashconcepts.wallpaperhd4k.utils.Constants
import com.hashconcepts.wallpaperhd4k.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel @Inject constructor(
    private val repository: WallpaperRepository,
) : ViewModel() {


    private val _wallpaperLiveData = MutableLiveData<Resource<List<Photo>>>()
    val wallpaperLiveData: LiveData<Resource<List<Photo>>> = _wallpaperLiveData


    init {
        retrieveSavedWallpaper()
    }

    private fun retrieveSavedWallpaper() = viewModelScope.launch(Dispatchers.IO) {
        _wallpaperLiveData.postValue(Resource.Loading())
        try {
            val response = repository.getFavouriteWallpapers()
            Timber.d(response.toString())
            _wallpaperLiveData.postValue(Resource.Success(response))
        } catch (e: Exception) {
            _wallpaperLiveData.postValue(Resource.Error(e.localizedMessage ?: Constants.UNEXPECTED_ERROR))
        }
    }
}