package com.example.unsplashapp.di

import com.example.unsplashapp.api.unsplashAPI
import com.example.unsplashapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.internal.http.RetryAndFollowUpInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit {
        val client = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS) // Increase connect timeout to 30 seconds
            .readTimeout(30, TimeUnit.SECONDS) // Increase read timeout to 30 seconds
            .writeTimeout(30, TimeUnit.SECONDS) // Increase write timeout to 30 seconds
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    @Singleton
    @Provides
    fun providesUnSplashAPI(retrofit: Retrofit): unsplashAPI {
        return retrofit.create(unsplashAPI::class.java)
    }
}