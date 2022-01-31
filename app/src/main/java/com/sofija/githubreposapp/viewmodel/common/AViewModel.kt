package com.sofija.githubreposapp.viewmodel.common

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class AViewModel<T> : ViewModel() {

    protected val _data = MutableLiveData<T>()
    val data: LiveData<T> get() = _data

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }
    val errorMessage = MutableLiveData<String>()

    fun onError(message: String) {
        errorMessage.value = message
    }
}