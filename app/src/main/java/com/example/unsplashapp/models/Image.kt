package com.example.unsplashapp.models

import androidx.annotation.Keep

@Keep
data class Image(
    val id: String,
    val urls: Urls
)