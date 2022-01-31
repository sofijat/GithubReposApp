package com.sofija.githubreposapp.repository

import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.webservices.GitHubApiService

object GitHubRepositoryProvider {
    fun provideGitHubRepository(): GitHubRepository {
        return GitHubRepository(GitHubApiService.create())
    }
}