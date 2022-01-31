package com.sofija.githubreposapp.viewmodel.repos

import com.sofija.githubreposapp.model.Repo
import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.viewmodel.common.AViewModel
import kotlinx.coroutines.*

class ReposViewModel(private val gitHubRepository: GitHubRepository) : AViewModel<List<Repo>>() {
    fun getReposForUser(username: String) {
        var job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = gitHubRepository.searchReposForUserByUsername(username)
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