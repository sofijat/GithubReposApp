package com.sofija.githubreposapp.view.repos

import com.sofija.githubreposapp.model.Repo

interface RepoCardClickListener {
    fun onRepoCardClicked(repo: Repo)
}