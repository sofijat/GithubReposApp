package com.sofija.githubreposapp.view.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sofija.githubreposapp.R
import com.sofija.githubreposapp.model.Repo
import com.sofija.githubreposapp.model.User
import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.view.commits.CommitsFragment
import com.sofija.githubreposapp.view.profile.ProfileFragment
import com.sofija.githubreposapp.view.repos.RepoCardClickListener
import com.sofija.githubreposapp.view.repos.ReposAdapter
import com.sofija.githubreposapp.view.repos.ReposFragment
import com.sofija.githubreposapp.viewmodel.main.MainActivityViewModel
import com.sofija.githubreposapp.viewmodel.main.MainActivityViewModelFactory
import com.sofija.githubreposapp.webservices.GitHubApiService


class MainActivity : AppCompatActivity(), RepoCardClickListener {

    companion object {
        val REPOS_FRAGMENT_TAG = "REPOS_FRAGMENT_TAG"
        val PROFILE_FRAGMENT_TAG = "PROFILE_FRAGMENT_TAG"
        val COMMITS_FRAGMENT_TAG = "COMMITS_FRAGMENT_TAG"
    }

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var currentUser: User

    override fun onStart() {
        super.onStart()
        ReposAdapter.registerRepoCardClickListener(this)
    }

    override fun onStop() {
        super.onStop()
        ReposAdapter.unregisterRepoCardClickListener(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        setDataListeners()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, MainActivityViewModelFactory(
            GitHubRepository(
                GitHubApiService.create())
        )
        ).get(MainActivityViewModel::class.java)
        viewModel.getUser()
    }

    private fun setDataListeners(){
        setListenerOnGetUser()
    }

    private fun setListenerOnGetUser(){
        viewModel.data.observe(this, Observer { user ->
            onUserFetched(user)
        })
    }

    private fun onUserFetched(user: User?) {
        user?.let {
            currentUser = it
            startReposFragment(user)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.menuItemProfile -> {
                startProfileFragment(currentUser)
            }
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        changeSupportBarTitle(resources.getString(R.string.app_name))
        if (count == 0) {
            super.onBackPressed()
        } else {
            supportFragmentManager.popBackStack()
        }
    }

    private fun startReposFragment(user: User){
        supportFragmentManager.beginTransaction().replace(R.id.main_container, ReposFragment.newInstance(user), REPOS_FRAGMENT_TAG).addToBackStack(
            REPOS_FRAGMENT_TAG
        ).commit()
    }

    private fun startProfileFragment(user: User){
        supportFragmentManager.beginTransaction().replace(R.id.main_container, ProfileFragment.newInstance(user), PROFILE_FRAGMENT_TAG).addToBackStack(
            PROFILE_FRAGMENT_TAG
        ).commit()
        changeSupportBarTitle(resources.getString(R.string.profile))
    }

    private fun startCommitsFragment(user: User, repo: Repo){
        supportFragmentManager.beginTransaction().replace(R.id.main_container, CommitsFragment.newInstance(user, repo), COMMITS_FRAGMENT_TAG).addToBackStack(
            COMMITS_FRAGMENT_TAG
        ).commit()
        changeSupportBarTitle(repo.name)
    }

    override fun onRepoCardClicked(repo: Repo) {
        startCommitsFragment(currentUser, repo)
    }

    private fun changeSupportBarTitle(newTitle: String){
        supportActionBar?.title = newTitle
    }
}