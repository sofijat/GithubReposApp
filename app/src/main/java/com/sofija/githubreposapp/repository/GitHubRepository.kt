package com.sofija.githubreposapp.repository

import com.sofija.githubreposapp.webservices.GitHubApiService

class GitHubRepository(private val apiService: GitHubApiService) {

    companion object Auth {
        val USERNAME = "octocat"
        val TOKEN = "token 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"
    }

    suspend fun searchUser() = apiService.fetchUser(TOKEN, USERNAME)

    suspend fun searchReposForUserByUsername(username: String) = apiService.fetchRepos(TOKEN, username)

    suspend fun searchCommitsForOwnerAndRepo(owner: String, repo: String, page: Int) = apiService.fetchCommitsForOwnerAndRepo(TOKEN, owner, repo, page)

}