package com.sofija.githubreposapp.webservices

import com.sofija.githubreposapp.model.RepoCommit
import com.sofija.githubreposapp.model.User
import com.sofija.githubreposapp.model.Repo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubApiService {

    /**
     * Companion object to create the GithubApiService
     */
    companion object Factory {
        fun create(): GitHubApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://api.github.com/")
                .build()

            return retrofit.create(GitHubApiService::class.java);
        }
    }

    @GET("users/{username}")
    suspend fun fetchUser(@Header("Authorization") accessToken: String, @Path("username") username: String): Response<User>

    @GET("users/{user}/repos")
    suspend fun fetchRepos(@Header("Authorization") accessToken: String, @Path("user") user: String): Response<List<Repo>>

    @GET("repos/{owner}/{repo}/commits")
    suspend fun fetchCommitsForOwnerAndRepo(@Header("Authorization") accessToken: String, @Path("owner") owner: String, @Path("repo") repo: String, @Query("page") page: Int): Response<List<RepoCommit>>

}