package com.hashconcepts.wallpaperhd4k.data.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hashconcepts.wallpaperhd4k.data.models.Photo
import com.hashconcepts.wallpaperhd4k.data.remote.ServiceApi
import java.lang.Exception
import javax.inject.Inject

/**
 * @created 17/01/2022 - 9:19 PM
 * @project Wallpaper HD4K
 * @author  ifechukwu.udorji
 */
class WallpaperCategoryDataSource @Inject constructor(
    private val serviceApi: ServiceApi,
    private val category: String
): PagingSource<Int, Photo>() {

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = serviceApi.getImagesByCategory(nextPageNumber, category)
            LoadResult.Page(
                data = response.photos,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber < response.total_results) nextPageNumber + 1 else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}