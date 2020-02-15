package com.jonilson.books.ui.viewmodel

import androidx.lifecycle.*
import com.jonilson.books.http.BookHttp
import com.jonilson.books.model.Volume
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookListViewModel: ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State>
        get() = _state

    fun loadBooks() {
        if (state.value != null) return

        viewModelScope.launch {
            _state.value = State.StateLoading
            delay(3000)
            val result = withContext(Dispatchers.IO) {
                BookHttp.searchBook("Dominando o Android")
            }
            if (result?.items != null) {
                _state.value = State.StateLoaded(result.items)
            } else {
                _state.value = State.StateError(Exception("No results"), false)
            }
        }
    }

    sealed class State {
        object StateLoading: State()
        data class StateLoaded(val list: List<Volume>): State()
        data class StateError(val error: Throwable, var hasConsumed: Boolean): State()
    }
}