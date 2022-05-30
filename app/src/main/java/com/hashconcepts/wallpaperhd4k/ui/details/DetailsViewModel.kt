package com.hashconcepts.wallpaperhd4k.ui.details

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.*
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperRepository
import com.hashconcepts.wallpaperhd4k.utils.NetworkManager
import com.hashconcepts.wallpaperhd4k.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import timber.log.Timber
import java.io.*
import javax.inject.Inject
import android.graphics.BitmapFactory
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.utils.Constants.IMAGES_FOLDER_NAME
import com.hashconcepts.wallpaperhd4k.utils.Constants.UNEXPECTED_ERROR


@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: WallpaperRepository,
    networkManager: NetworkManager,
    private val context: Context
) : ViewModel() {

    private val networkObserver = networkManager.observeConnectionStatus

    private val _imageLiveData = MutableLiveData<Resource<String>>()
    val imageLiveData: LiveData<Resource<String>> = _imageLiveData

    var bitmapImage: Bitmap? = null

    private val _favouriteWallpaper = MutableLiveData<Boolean>()
    val favouriteWallpaper: LiveData<Boolean> = _favouriteWallpaper


    fun checkWallpaperIsFavourite(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val wallpaper = repository.checkWallpaperExist(id)
            if (wallpaper == null) {
                _favouriteWallpaper.postValue(false)
            } else {
                _favouriteWallpaper.postValue(true)
            }
        } catch (e: Exception) {
            _favouriteWallpaper.postValue(false)
        }
    }

    fun saveWallpaper(photo: Photo) = viewModelScope.launch(Dispatchers.IO) {
        _imageLiveData.postValue(Resource.Loading())
        try {
            repository.saveFavouriteWallpaper(photo)
            _imageLiveData.postValue(Resource.Success("Wallpaper saved"))
            _favouriteWallpaper.postValue(true)
        } catch (e: Exception) {
            _imageLiveData.postValue(Resource.Error(e.localizedMessage ?: UNEXPECTED_ERROR))
        }
    }

    fun downloadImage(imageUrl: String) = viewModelScope.launch(Dispatchers.IO) {
        _imageLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val response = repository.downloadImage(imageUrl)
                val bitmap = BitmapFactory.decodeStream(response.byteStream())
                saveBitmap(bitmap)
            } catch (e: Exception) {
                _imageLiveData.postValue(Resource.Error(e.localizedMessage ?: UNEXPECTED_ERROR))
            }
        } else {
            _imageLiveData.postValue(
                Resource.Error(
                    "internet not available, check connection"
                )
            )
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val outputStream: OutputStream?
        val name = "wallpaper_hd4k_${System.currentTimeMillis()}"

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val resolver: ContentResolver = context.contentResolver
                val contentValues = ContentValues()
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/$IMAGES_FOLDER_NAME")
                val imageUri =
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                outputStream = resolver.openOutputStream(imageUri!!)
            } else {
                val imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM
                ).toString() + File.separator + IMAGES_FOLDER_NAME
                val file = File(imagesDir)
                if (!file.exists()) {
                    file.mkdir()
                }
                val image = File(imagesDir, "$name.jpeg")
                outputStream = FileOutputStream(image)
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            bitmapImage = bitmap
            _imageLiveData.postValue(Resource.Success("Wallpaper downloaded successfully."))

            outputStream!!.flush()
            outputStream.close()
        } catch (e: IOException) {
            Timber.d(":::::::::::::::::::: EXCEPTION :::::::::::::::" + e.localizedMessage)
        }
    }

    fun deleteWallpaper(photo: Photo) = viewModelScope.launch(Dispatchers.IO) {
        _imageLiveData.postValue(Resource.Loading())
        try {
            repository.deleteFavouriteWallpaper(photo)
            _imageLiveData.postValue(Resource.Success("Wallpaper deleted"))
            _favouriteWallpaper.postValue(false)
        } catch (e: Exception) {
            _imageLiveData.postValue(Resource.Error(e.localizedMessage ?: UNEXPECTED_ERROR))
        }
    }

}