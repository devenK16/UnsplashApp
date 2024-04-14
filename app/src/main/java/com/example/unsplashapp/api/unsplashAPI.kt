package com.example.unsplashapp.api

import com.example.unsplashapp.models.Image
import retrofit2.Response
import retrofit2.http.GET

interface unsplashAPI {
    @GET("/photos?client_id=jsJzMhc1hlkBJn69sV6cd2L2gfTLt1hvWwjYfwQhDCQ")
    suspend fun getPhotos() : Response<List<Image>>
}