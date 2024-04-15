package com.example.unsplashapp.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplashapp.R
import com.example.unsplashapp.ui.adapter.ImageAdapter
import com.example.unsplashapp.util.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var imageAdapter: ImageAdapter
    private lateinit var progressBar: ProgressBar
    private lateinit var photosViewModel : PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        enableEdgeToEdge()
        photosViewModel = ViewModelProvider(this).get(PhotosViewModel::class.java)
        photosViewModel.getPhotos()
        setupRecyclerView()
        bindObservers()
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        imageAdapter = ImageAdapter(this)
        recyclerView.adapter = imageAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.setHasFixedSize(true)
        recyclerView.setItemViewCacheSize(10)
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!photosViewModel.isLoading && lastVisibleItem >= totalItemCount - 1 ) {
                    photosViewModel.getPhotos()
                }
            }
        })
    }

    private fun bindObservers() {
        photosViewModel.photosLiveData.observe(this, Observer {
            when (it) {
                is NetworkResult.Success -> {
                    progressBar.visibility = View.GONE
                    imageAdapter.addImages(it.data!!)
                }
                is NetworkResult.Error -> {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message ?: "An error occurred", Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading -> {
                    progressBar.visibility = View.VISIBLE
                }
            }
        })
    }
}