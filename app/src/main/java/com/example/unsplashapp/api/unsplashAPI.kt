package com.example.unsplashapp.api

import com.example.unsplashapp.models.Image
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface unsplashAPI {
    @GET("/photos")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("client_id") clientId: String,
        @Query("per_page") perPage : Int = 14
    ): Response<List<Image>>
}