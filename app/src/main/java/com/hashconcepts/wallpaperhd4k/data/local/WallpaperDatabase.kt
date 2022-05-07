package com.hashconcepts.wallpaperhd4k.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.hashconcepts.wallpaperhd4k.data.models.Photo

/**
 * @created 06/05/2022 - 5:41 PM
 * @project Wallpaper HD4K
 * @author  ifechukwu.udorji
 */

@Database(
    entities = [Photo::class],
    exportSchema = false,
    version = 2
)
@TypeConverters(WallpaperConverter::class)
abstract class WallpaperDatabase : RoomDatabase() {

    abstract fun getWallpaperDao() : WallpaperDao
}