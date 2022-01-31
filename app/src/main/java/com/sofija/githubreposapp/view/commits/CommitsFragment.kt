package com.sofija.githubreposapp.view.commits

import android.os.Bundle
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sofija.githubreposapp.R
import com.sofija.githubreposapp.databinding.CommitsFragmentBinding
import com.sofija.githubreposapp.model.RepoCommit
import com.sofija.githubreposapp.model.Repo
import com.sofija.githubreposapp.model.User
import com.sofija.githubreposapp.repository.GitHubRepository
import com.sofija.githubreposapp.util.Util
import com.sofija.githubreposapp.view.ARecyclerViewFragment
import com.sofija.githubreposapp.viewmodel.commits.CommitsViewModel
import com.sofija.githubreposapp.viewmodel.commits.CommitsViewModelFactory
import com.sofija.githubreposapp.webservices.GitHubApiService

class CommitsFragment(): ARecyclerViewFragment<CommitsFragmentBinding>() {

    companion object {
        fun newInstance(user: User, repo: Repo): CommitsFragment {
            val args = Bundle()
            args.putSerializable("user", user)
            args.putSerializable("repo", repo)
            val rf = CommitsFragment(user, repo)
            rf.arguments = args
            return rf
        }
    }

    private lateinit var adapter: CommitsAdapter
    private lateinit var viewModel: CommitsViewModel
    private lateinit var user: User
    private lateinit var repo: Repo
    private var currentPage = 1
    private var isLastPage = false

    constructor(currentUser: User, currentRepo: Repo): this(){
        user = currentUser
        repo = currentRepo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        user = arguments?.get("user") as User
        repo = arguments?.get("repo") as Repo
        super.onCreate(savedInstanceState)
    }

    override fun setup() {
        updateProgressBarVisibility()
        initViewModel()
        initAdapter()
        setDataListeners()
        setScrollListener()
    }

    private fun setScrollListener(){
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.addOnScrollListener(object : PaginationListener(layoutManager){
            override fun loadMoreItems() {
                isLoading = true
                updateProgressBarVisibility()
                currentPage++
                viewModel.getCommitsForRepo(user.login, repo, currentPage)
            }
            override val isLastPageOfData: Boolean
                get() = isLastPage
            override val isLoadingData: Boolean
                get() = isLoading

        })
    }

    private fun initViewModel(){
        viewModel = ViewModelProvider(this, CommitsViewModelFactory(
            GitHubRepository(
                GitHubApiService.create())
        )
        ).get(CommitsViewModel::class.java)
        viewModel.getCommitsForRepo(user.login, repo, 1)
    }

    private fun initAdapter(){
        adapter = CommitsAdapter()
        val layoutManager = LinearLayoutManager(context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
    }

    private fun setDataListeners(){
        setListenerOnGetCommits()
        setListenerOnErrors()
    }

    private fun setListenerOnGetCommits(){
        viewModel.data.observe(viewLifecycleOwner, Observer { commits ->
            onCommitsFetched(commits)
        })
    }

    private fun setListenerOnErrors(){
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer { error ->
            Util.makeToast(error, context)
        })
    }

    private fun onCommitsFetched(commits: List<RepoCommit>){
        adapter.setItems(commits)
        isLoading = false
        if (commits.size < PaginationListener.PAGE_START){
            isLastPage = true
        }
        updateProgressBarVisibility()
    }

    private fun updateProgressBarVisibility(){
        if (isLoading){
            binding.progressBarCyclic.visibility = View.VISIBLE
        } else {
            binding.progressBarCyclic.visibility = View.GONE
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> CommitsFragmentBinding
            = CommitsFragmentBinding::inflate

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.menuItemProfile)?.isVisible = false
        super.onPrepareOptionsMenu(menu)
    }

}