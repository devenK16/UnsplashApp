package com.example.unsplashapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.unsplashapp.api.unsplashAPI
import com.example.unsplashapp.models.Image
import com.example.unsplashapp.util.NetworkResult
import org.json.JSONObject
import javax.inject.Inject

class PhotosRepository @Inject constructor(
    private val unsplashAPI: unsplashAPI
) {
    private val _photosLiveData = MutableLiveData<NetworkResult<List<Image>>>()
    val photosLiveData: LiveData<NetworkResult<List<Image>>>
        get() = _photosLiveData

    private var currentPage = 1
    private var hasMorePages = true
    suspend fun getPhotos(){
        if (!hasMorePages) return
        _photosLiveData.postValue(NetworkResult.Loading())

        val response = unsplashAPI.getPhotos(currentPage, "jsJzMhc1hlkBJn69sV6cd2L2gfTLt1hvWwjYfwQhDCQ")

        Log.d("unsplashTest" , response.body().toString())
        if( response.isSuccessful && response.body() != null ){
            _photosLiveData.postValue(NetworkResult.Success(response.body()!!))
            currentPage += 2
            hasMorePages = response.body()!!.size == 14
        } else if( response.errorBody() != null ){
            val errorMessage = response.errorBody()?.string() ?: "Something went wrong"
            _photosLiveData.postValue(NetworkResult.Error(errorMessage))
            hasMorePages = false
        } else {
            _photosLiveData.postValue(NetworkResult.Error("Something went wrong"))
            hasMorePages = false
        }
    }
}