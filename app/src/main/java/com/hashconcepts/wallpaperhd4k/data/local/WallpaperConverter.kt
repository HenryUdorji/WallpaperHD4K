package com.hashconcepts.wallpaperhd4k.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hashconcepts.wallpaperhd4k.data.models.Src

/**
 * @created 07/05/2022 - 2:25 PM
 * @project Wallpaper HD4K
 * @author  ifechukwu.udorji
 */
class WallpaperConverter {

    @TypeConverter
    fun fromSrc(src: Src): String {
        return Gson().toJson(src)
    }

    @TypeConverter
    fun toString(jsonSrc: String): Src {
        val srcType = object : TypeToken<Src>() {}.type
        return Gson().fromJson(jsonSrc, srcType)
    }
}