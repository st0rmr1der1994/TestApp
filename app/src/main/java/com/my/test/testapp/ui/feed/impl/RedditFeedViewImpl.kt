package com.my.test.testapp.ui.feed.impl

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.my.test.testapp.R
import com.my.test.testapp.di.component.FeedComponent
import com.my.test.testapp.di.module.RedditFeedModule
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedView
import com.my.test.testapp.ui.feed.util.RecyclerEndlessScrollListener
import com.my.test.testapp.ui.feed.util.RecyclerItemClickListener
import com.my.test.testapp.ui.feed.util.RedditFeedAdapter
import kotlinx.android.synthetic.main.view_screen_feed.*
import javax.inject.Inject

private const val STATE_KEY_POSTS = "RedditFeedViewImpl#state_key_posts"

class RedditFeedViewImpl : PresentableDaggerController<RedditFeedView, RedditFeedPresenter>(), RedditFeedView {

    @Inject
    internal lateinit var feedPresenter: RedditFeedPresenter
    @Inject
    internal lateinit var layoutManager: RecyclerView.LayoutManager
    @Inject
    internal lateinit var feedAdapter: RedditFeedAdapter

    internal lateinit var feedComponent: FeedComponent

    private val scrollListener = RecyclerEndlessScrollListener { presenter.loadMorePosts() }

    override val presenter: RedditFeedPresenter
        get() = feedPresenter

    override fun onFinishInflate(view: View) {
        super.onFinishInflate(view)
        feedRecyclerView.layoutManager = layoutManager
        feedRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        feedRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context!!) {
            presenter.openPostDetail(feedAdapter.getItemByPosition(it))

        })
        feedRecyclerView.addOnScrollListener(scrollListener)
        feedRecyclerView.adapter = feedAdapter

        feedSwipeToRefreshLayout.setOnRefreshListener { presenter.forceLoadPosts() }
        feedRetryButton.setOnClickListener { presenter.forceLoadPosts() }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if (feedAdapter.isEmpty()) {
            presenter.loadPosts()
        }
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        outState.putParcelableArrayList(STATE_KEY_POSTS, ArrayList(feedAdapter.items))
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        feedAdapter.addItems(savedViewState.getParcelableArrayList(STATE_KEY_POSTS))
    }

    override fun showLoading() {
        feedSwipeToRefreshLayout.isRefreshing = true
        scrollListener.isLoading = true
    }

    override fun hideLoading() {
        feedSwipeToRefreshLayout.isRefreshing = false
        scrollListener.isLoading = false
    }

    override fun showError() {
        feedErrorLayout.visibility = VISIBLE
    }

    override fun hideError() {
        feedErrorLayout.visibility = GONE
    }

    override fun showLoadedPosts(posts: List<RedditPostModel>) {
        feedAdapter.addItems(posts)
    }

    override fun initializeInjector() {
        val componentBuilder = activityComponent.feedComponentBuilder()
        componentBuilder.feedModule(RedditFeedModule())
        feedComponent = componentBuilder.build()
        feedComponent.inject(this)
    }

    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.view_screen_feed , viewGroup, false)

}
