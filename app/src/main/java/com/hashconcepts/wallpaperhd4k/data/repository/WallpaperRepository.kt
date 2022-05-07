package com.hashconcepts.wallpaperhd4k.data.repository

import com.hashconcepts.wallpaperhd4k.data.local.WallpaperDao
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.data.remote.ServiceApi
import javax.inject.Inject

class WallpaperRepository @Inject constructor(
    private val serviceApi: ServiceApi,
    private val wallpaperDao: WallpaperDao
) {
    suspend fun downloadImage(imageUrl: String) = serviceApi.downloadImage(imageUrl)
    suspend fun saveFavouriteWallpaper(photo: Photo) = wallpaperDao.saveFavouriteWallpaper(photo)
    suspend fun getFavouriteWallpapers() = wallpaperDao.getFavouriteWallpapers()
    suspend fun checkWallpaperExist(id: Int) = wallpaperDao.checkWallpaperExist(id)
    suspend fun deleteFavouriteWallpaper(photo: Photo) = wallpaperDao.deleteFavouriteWallpaper(photo)
}
