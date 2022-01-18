package com.hashconcepts.wallpaperhd4k.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo (
    val alt: String,
    val avg_color: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val photographer_id: Int,
    val photographer_url: String,
    val src: Src,
    val url: String,
    val width: Int
): Parcelable