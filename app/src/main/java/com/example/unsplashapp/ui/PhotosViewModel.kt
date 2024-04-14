package com.example.unsplashapp.ui

import androidx.lifecycle.ViewModel
import com.example.unsplashapp.repository.PhotosRepository
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotosViewModel @Inject constructor(
    private val photosRepository: PhotosRepository
) : ViewModel() {

    val photosLiveData get() = photosRepository.photosLiveData
    fun getPhotos(){
        viewModelScope.launch{
            photosRepository.getPhotos()
        }
    }
}