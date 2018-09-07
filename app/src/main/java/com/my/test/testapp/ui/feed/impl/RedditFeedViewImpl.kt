package com.my.test.testapp.ui.feed.impl

import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.my.test.testapp.R
import com.my.test.testapp.di.component.DaggerFeedComponent
import com.my.test.testapp.di.component.FeedComponent
import com.my.test.testapp.di.module.RedditFeedModule
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.navigation.go
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.detail.impl.RedditDetailViewImpl
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedView
import com.my.test.testapp.ui.feed.util.RecyclerItemClickListener
import com.my.test.testapp.ui.feed.util.RedditFeedAdapter
import kotlinx.android.synthetic.main.view_screen_feed.*
import javax.inject.Inject

class RedditFeedViewImpl : PresentableDaggerController<RedditFeedView, RedditFeedPresenter>(), RedditFeedView {

    @Inject
    internal lateinit var feedPresenter: RedditFeedPresenter
    internal lateinit var feedComponent: FeedComponent

    private val feedAdapter = RedditFeedAdapter(ArrayList())

    override val presenter: RedditFeedPresenter
        get() = feedPresenter

    override fun onFinishInflate(view: View) {
        super.onFinishInflate(view)
        feedRecyclerView.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        feedRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        feedRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context!!,
                object : RecyclerItemClickListener.OnItemClickListener {
                    @Suppress("UnsafeCast")
                    override fun onItemClick(position: Int) {
                        val postModel = feedAdapter.getItemByPosition(position)
                        router.go(RedditDetailViewImpl.create(postModel))
                    }
                }))
        feedRecyclerView.adapter = feedAdapter

        feedSwipeToRefreshLayout.setOnRefreshListener { presenter.loadPosts() }
        feedRetryButton.setOnClickListener { presenter.loadPosts() }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        if (feedAdapter.isEmpty()) {
            presenter.loadPosts()
        }
    }

    override fun showLoading() {
        feedSwipeToRefreshLayout.isRefreshing = true
    }

    override fun hideLoading() {
        feedSwipeToRefreshLayout.isRefreshing = false
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
        feedComponent = DaggerFeedComponent.builder()
                .applicationComponent(appComponent())
                .redditFeedModule(RedditFeedModule())
                .build()
        feedComponent.inject(this)
    }

    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.view_screen_feed , viewGroup, false)

}
