package com.hashconcepts.wallpaperhd4k.ui.details

import android.app.Application
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.*
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperRepository
import com.hashconcepts.wallpaperhd4k.utils.NetworkManager
import com.hashconcepts.wallpaperhd4k.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import java.lang.Exception
import timber.log.Timber
import java.io.*
import javax.inject.Inject
import android.graphics.BitmapFactory




@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: WallpaperRepository,
    private val networkManager: NetworkManager,
    private val context: Context
) : ViewModel() {

    private val networkObserver = networkManager.observeConnectionStatus

    private val _downloadImageLiveData = MutableLiveData<Resource<String>>()
    val downloadImageLiveData: LiveData<Resource<String>> = _downloadImageLiveData


    fun downloadImage(imageUrl: String) = viewModelScope.launch (Dispatchers.IO) {
        _downloadImageLiveData.postValue(Resource.Loading())
        if (networkObserver.value == true) {
            try {
                val response = repository.downloadImage(imageUrl)
                val bitmap = BitmapFactory.decodeStream(response.byteStream())
                saveBitmap(bitmap)
            } catch (e: Exception) {
                _downloadImageLiveData.postValue(Resource.Error(e.localizedMessage))
            }
        } else {
            _downloadImageLiveData.postValue(Resource.Error(
                "internet not available, check connection"))
        }
    }

    private fun saveBitmap(bitmap: Bitmap) {
        val outputStream: OutputStream?
        val name = "wallpaper_hd4k_${System.currentTimeMillis()}"
        val IMAGES_FOLDER_NAME = "Wallpaper HD4K"
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
                _downloadImageLiveData.postValue(Resource.Success("Wallpaper downloaded successfully."))
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
                _downloadImageLiveData.postValue(Resource.Success("Wallpaper downloaded successfully."))
            }
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream!!.flush()
            outputStream.close()
        } catch (e: IOException) {
            Timber.d(":::::::::::::::::::: EXCEPTION :::::::::::::::" + e.localizedMessage)
        }
    }

}