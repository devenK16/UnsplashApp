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

    suspend fun getPhotos(){
        val response = unsplashAPI.getPhotos()
        Log.d("unsplashTest" , response.body().toString())
        if( response.isSuccessful && response.body() != null ){
            _photosLiveData.postValue(NetworkResult.Success(response.body()!!))
        } else if( response.errorBody() != null ){
            val errorObj = JSONObject(response.errorBody()!!.charStream().readText())
            _photosLiveData.postValue(NetworkResult.Error(errorObj.getString("message")))
        } else {
            _photosLiveData.postValue(NetworkResult.Error("Something went wrong"))
        }
    }
}