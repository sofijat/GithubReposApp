package com.sofija.githubreposapp.viewmodel.commits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sofija.githubreposapp.repository.GitHubRepository

class CommitsViewModelFactory constructor(private val repository: GitHubRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(CommitsViewModel::class.java)) {
            CommitsViewModel(this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}