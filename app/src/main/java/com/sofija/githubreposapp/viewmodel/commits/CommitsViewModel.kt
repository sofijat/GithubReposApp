package com.sofija.githubreposapp.viewmodel.commits

import com.sofija.githubreposapp.model.RepoCommit
import com.sofija.githubreposapp.model.Repo
import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.viewmodel.common.AViewModel
import kotlinx.coroutines.*

class CommitsViewModel(private val gitHubRepository: GitHubRepository) : AViewModel<List<RepoCommit>>() {

    fun getCommitsForRepo(owner: String, repo: Repo, page: Int) {
        var job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = gitHubRepository.searchCommitsForOwnerAndRepo(owner, repo.name, page)
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