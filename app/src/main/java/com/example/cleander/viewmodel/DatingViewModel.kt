package com.example.cleander.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cleander.model.Album
import com.example.cleander.model.AlbumDataProvider

class DatingViewModel : ViewModel() {
    private val _albumLiveData = MutableLiveData<MutableList<Album>>()
    val albumLiveData: LiveData<MutableList<Album>> = _albumLiveData

    init {
        getAlbums()
    }

    private fun getAlbums() {
        _albumLiveData.value = AlbumDataProvider.photos.take(15).toMutableList()
    }
}