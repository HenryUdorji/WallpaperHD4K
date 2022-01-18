package com.hashconcepts.wallpaperhd4k.data.remote

import com.hashconcepts.wallpaperhd4k.data.models.WallpaperResponse
import com.hashconcepts.wallpaperhd4k.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query


interface ServiceApi {

    @Headers("Authorization: Bearer ${Constants.API_KEY}")
    @GET("curated?per_page=30")
    suspend fun getTrendingImages(
        @Query("page")
        page: Int = 1
    ): WallpaperResponse
}
