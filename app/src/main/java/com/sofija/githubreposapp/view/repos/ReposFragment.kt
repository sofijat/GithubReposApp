package com.sofija.githubreposapp.view.repos

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sofija.githubreposapp.R
import com.sofija.githubreposapp.databinding.ReposFragmentBinding
import com.sofija.githubreposapp.model.Repo
import com.sofija.githubreposapp.model.User
import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.util.Util
import com.sofija.githubreposapp.view.ARecyclerViewFragment
import com.sofija.githubreposapp.viewmodel.repos.ReposViewModel
import com.sofija.githubreposapp.viewmodel.repos.ReposViewModelFactory
import com.sofija.githubreposapp.webservices.GitHubApiService

class ReposFragment() : ARecyclerViewFragment<ReposFragmentBinding>() {

    companion object {
        fun newInstance(user: User): ReposFragment {
            val args = Bundle()
            args.putSerializable("user", user)
            val rf = ReposFragment(user)
            rf.arguments = args
            return rf
        }
    }

    private lateinit var adapter: ReposAdapter
    private lateinit var viewModel: ReposViewModel
    private lateinit var user: User

    constructor(currentUser: User): this(){
        user = currentUser
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        user = arguments?.get("user") as User
        super.onCreate(savedInstanceState)
    }

    override fun setup() {
        updateProgressBarVisibility()
        initViewModel()
        initAdapter()
        setDataListeners()
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, ReposViewModelFactory(
            GitHubRepository(
                GitHubApiService.create())
        )
        ).get(ReposViewModel::class.java)
        viewModel.getReposForUser(user.login)
    }

    private fun initAdapter(){
        adapter = ReposAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun setDataListeners(){
        setListenerOnGetRepos()
        setListenerOnErrors()
    }

    private fun setListenerOnGetRepos(){
        viewModel.data.observe(viewLifecycleOwner, Observer { repos ->
            onReposFetched(repos)
        })
    }

    private fun setListenerOnErrors(){
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            Util.makeToast(error, context)
        })
    }

    private fun onReposFetched(repos: List<Repo>){
        adapter.setItems(repos)
        isLoading = false
        updateProgressBarVisibility()
    }

    private fun updateProgressBarVisibility(){
        if (isLoading){
            binding.progressBarCyclic.visibility = View.VISIBLE
        } else {
            binding.progressBarCyclic.visibility = View.GONE
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menuItemProfile)?.isVisible = true
        super.onPrepareOptionsMenu(menu)
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ReposFragmentBinding
            = ReposFragmentBinding::inflate

}