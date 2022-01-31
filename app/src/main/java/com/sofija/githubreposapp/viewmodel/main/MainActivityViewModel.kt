package com.sofija.githubreposapp.viewmodel.main

import com.sofija.githubreposapp.model.User
import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.viewmodel.common.AViewModel
import kotlinx.coroutines.*

class MainActivityViewModel(private val gitHubRepository: GitHubRepository) : AViewModel<User>() {

    fun getUser() {
        var job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = gitHubRepository.searchUser()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    _data.postValue(response.body())
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }
}