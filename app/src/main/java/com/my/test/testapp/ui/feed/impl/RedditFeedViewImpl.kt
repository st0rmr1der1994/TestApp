package com.my.test.testapp.ui.feed.impl

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.my.test.testapp.R
import com.my.test.testapp.di.component.FeedComponent
import com.my.test.testapp.di.module.FOR_LANDSCAPE
import com.my.test.testapp.di.module.FOR_PORTRAIT
import com.my.test.testapp.di.module.RedditFeedModule
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.navigation.go
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.detail.impl.RedditDetailViewImpl
import com.my.test.testapp.ui.feed.RedditFeedPresenter
import com.my.test.testapp.ui.feed.RedditFeedView
import com.my.test.testapp.ui.feed.util.FixedCountLayoutManager
import com.my.test.testapp.ui.feed.util.RecyclerEndlessScrollListener
import com.my.test.testapp.ui.feed.util.RecyclerItemClickListener
import com.my.test.testapp.ui.feed.util.RedditFeedAdapter
import com.my.test.testapp.utils.ITEMS_PER_PAGE_HORIZONTAL
import com.my.test.testapp.utils.ITEMS_PER_PAGE_VERTICAL
import com.my.test.testapp.utils.OrientationChangedListener
import kotlinx.android.synthetic.main.view_screen_feed.*
import javax.inject.Inject
import javax.inject.Named

class RedditFeedViewImpl : PresentableDaggerController<RedditFeedView, RedditFeedPresenter>(), RedditFeedView, OrientationChangedListener {

    @Inject
    internal lateinit var feedPresenter: RedditFeedPresenter

    @field:[Inject Named(FOR_LANDSCAPE)]
    internal lateinit var landscapeLayoutManager: FixedCountLayoutManager

    @field:[Inject Named(FOR_PORTRAIT)]
    internal lateinit var portraitLayoutManager: FixedCountLayoutManager

    internal lateinit var feedComponent: FeedComponent

    private val feedAdapter = RedditFeedAdapter(ArrayList())

    override val presenter: RedditFeedPresenter
        get() = feedPresenter

    override var pageSize: Int = -1

    override fun onFinishInflate(view: View) {
        super.onFinishInflate(view)
        val currentOrientation = view.context.resources.configuration.orientation
        pageSize = pickSuitablePageSize(currentOrientation)
        feedRecyclerView.layoutManager = pickSuitableLayoutManager(currentOrientation)
        feedRecyclerView.addItemDecoration(DividerItemDecoration(context, VERTICAL))
        feedRecyclerView.addOnItemTouchListener(RecyclerItemClickListener(context!!) {
            val postModel = feedAdapter.getItemByPosition(it)
            router.go(RedditDetailViewImpl.create(postModel))

        })
        feedRecyclerView.addOnScrollListener(RecyclerEndlessScrollListener({ feedSwipeToRefreshLayout.isRefreshing = it }) { presenter.loadMorePosts() })
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

    override fun onOrientationChanged(newOrientation: Int) {
        feedRecyclerView.layoutManager = pickSuitableLayoutManager(newOrientation)
        pageSize = pickSuitablePageSize(newOrientation)
    }

    private fun pickSuitablePageSize(orientation: Int) = when(orientation) {
        ORIENTATION_PORTRAIT -> ITEMS_PER_PAGE_VERTICAL
        ORIENTATION_LANDSCAPE -> ITEMS_PER_PAGE_HORIZONTAL
        else -> { throw IllegalStateException("We cannot find supporting LayoutManager for this orientation") }
    }

    private fun pickSuitableLayoutManager(orientation: Int) = when(orientation) {
        ORIENTATION_PORTRAIT -> portraitLayoutManager
        ORIENTATION_LANDSCAPE -> landscapeLayoutManager
        else -> { throw IllegalStateException("We cannot find supporting LayoutManager for this orientation") }
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
        val componentBuilder = activityComponent.feedComponentBuilder()
        componentBuilder.feedModule(RedditFeedModule())
        feedComponent = componentBuilder.build()
        feedComponent.inject(this)
    }

    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.view_screen_feed , viewGroup, false)

}
