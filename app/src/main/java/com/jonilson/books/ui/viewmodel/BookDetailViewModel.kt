package com.jonilson.books.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jonilson.books.model.Volume
import com.jonilson.books.repository.BookRepository
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val repository: BookRepository
): ViewModel() {
    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavote: LiveData<Boolean> = _isFavorite

    fun onCreate(volume: Volume) {
        viewModelScope.launch {
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

    fun saveToFavorite(volume: Volume) {
        viewModelScope.launch {
            repository.save(volume)
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }

    fun removeFromFavorites(volume: Volume) {
        viewModelScope.launch {
            repository.delete(volume)
            _isFavorite.value = repository.isFavorite(volume.id)
        }
    }
}