package com.my.test.testapp.ui.feed.util

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.my.test.testapp.utils.ITEMS_PER_PAGE

class RecyclerEndlessScrollListener(
        private val loadMoreAction: () -> Unit
) : RecyclerView.OnScrollListener() {
    var isLoading: Boolean = false

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition()
        val visibleItemCount = recyclerView.childCount
        val totalItemCount = linearLayoutManager.itemCount
        if (dy > 0) {
            processScrollData(firstVisibleItemPosition, visibleItemCount, totalItemCount)
        }
    }

    private fun processScrollData(firstVisibleItemPosition: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= ITEMS_PER_PAGE) {
                loadMoreAction.invoke()
            }
        }
    }
}
