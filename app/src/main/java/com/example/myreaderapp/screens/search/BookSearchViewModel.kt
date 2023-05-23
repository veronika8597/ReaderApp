package com.example.myreaderapp.screens.search

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myreaderapp.data.DataOrException
import com.example.myreaderapp.model.Item
import com.example.myreaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookSearchViewModel @Inject constructor(private val repository: BookRepository):
ViewModel(){
    var listOfBooks: MutableState<DataOrException<List<Item>, Boolean, Exception>>
    = mutableStateOf(DataOrException(null, true, Exception("")))

    init {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch {
            if(query.isEmpty()){
                return@launch
            }
            listOfBooks.value.loading = true
            listOfBooks.value = repository.getBooks(query)

            Log.d( "DATA", "searchBooks: ${listOfBooks.value.data.toString()}")

            if (listOfBooks.value.data.toString().isNotEmpty()) listOfBooks.value.loading = false
        }

    }
}