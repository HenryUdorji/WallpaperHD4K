package com.hashconcepts.wallpaperhd4k.di

import android.app.Application
import android.content.Context
import com.hashconcepts.wallpaperhd4k.data.remote.ServiceApi
import com.hashconcepts.wallpaperhd4k.data.repository.WallpaperRepository
import com.hashconcepts.wallpaperhd4k.utils.Constants.BASE_URL
import com.hashconcepts.wallpaperhd4k.utils.NetworkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
internal object AppModule {

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideServiceApi(retrofit: Retrofit): ServiceApi =
        retrofit.create(ServiceApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(serviceApi: ServiceApi) = WallpaperRepository(serviceApi)

    @Provides
    @Singleton
    fun provideNetworkManager(@ApplicationContext context: Context) = NetworkManager(context)

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application.applicationContext
}