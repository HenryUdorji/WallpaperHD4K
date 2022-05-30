package com.hashconcepts.wallpaperhd4k.data.models

import androidx.annotation.Keep

@Keep
data class WallpaperResponse(
    val next_page: String,
    val page: Int,
    val per_page: Int,
    val photos: List<Photo>,
    val total_results: Int
)