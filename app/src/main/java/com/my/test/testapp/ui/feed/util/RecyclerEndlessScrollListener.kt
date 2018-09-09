package com.my.test.testapp.ui.feed.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class RecyclerEndlessScrollListener(
        private val loadingStateAction: (Boolean) -> Unit,
        private val loadMoreAction: () -> Unit
) : RecyclerView.OnScrollListener() {

    private var previousTotalItemCount: Int = 0
    private var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = linearLayoutManager.itemCount
        processScrollData(lastVisibleItemPosition, visibleItemCount, totalItemCount)
    }

    private fun processScrollData(lastVisibleItemPosition: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (totalItemCount < previousTotalItemCount) {
            this.previousTotalItemCount = totalItemCount
            if (totalItemCount == 0) {
                isLoading = true
            }
        }
        if (isLoading && (totalItemCount > previousTotalItemCount)) {
            isLoading = false
            previousTotalItemCount = totalItemCount
        }
        if (!isLoading && (lastVisibleItemPosition + visibleItemCount) > totalItemCount) {
            loadMoreAction.invoke()
            isLoading = true
        }
        loadingStateAction.invoke(isLoading)
    }
}
