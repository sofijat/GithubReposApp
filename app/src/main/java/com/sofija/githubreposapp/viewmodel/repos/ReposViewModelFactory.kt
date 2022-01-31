package com.sofija.githubreposapp.viewmodel.repos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sofija.githubreposapp.repository.GitHubRepository

class ReposViewModelFactory constructor(private val repository: GitHubRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ReposViewModel::class.java)) {
            ReposViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}