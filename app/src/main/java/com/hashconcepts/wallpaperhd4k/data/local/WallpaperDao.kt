package com.hashconcepts.wallpaperhd4k.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hashconcepts.wallpaperhd4k.data.models.Photo

/**
 * @created 06/05/2022 - 5:29 PM
 * @project Wallpaper HD4K
 * @author  ifechukwu.udorji
 */

@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFavouriteWallpaper(photo: Photo): Long

    @Query("SELECT * FROM Photo")
    suspend fun getFavouriteWallpapers(): List<Photo>

    @Query("SELECT * FROM Photo WHERE id = :id")
    suspend fun checkWallpaperExist(id: Int): Photo?

    @Delete
    suspend fun deleteFavouriteWallpaper(photo: Photo)
}