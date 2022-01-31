package com.sofija.githubreposapp.view.commits

import androidx.recyclerview.widget.RecyclerView


import androidx.recyclerview.widget.LinearLayoutManager

abstract class PaginationListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {

    companion object {
        const val PAGE_START = 1
        private const val PAGE_SIZE = 30
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        if (!isLoadingData && !isLastPageOfData) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= PAGE_SIZE) {
                loadMoreItems()
            }
        }
    }

    protected abstract fun loadMoreItems()
    abstract val isLastPageOfData: Boolean
    abstract val isLoadingData: Boolean

}