package com.my.test.testapp.ui.detail.impl

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.test.testapp.MainApplication
import com.my.test.testapp.R
import com.my.test.testapp.di.component.DaggerDetailComponent
import com.my.test.testapp.di.component.DetailComponent
import com.my.test.testapp.di.module.PresenterModule
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailView
import javax.inject.Inject
import kotlinx.android.synthetic.main.view_screen_detail.postDetailDraweeView
import kotlinx.android.synthetic.main.view_screen_detail.postTitleTextView
import kotlinx.android.synthetic.main.view_screen_detail.postAuthorTextView

private const val ARG_KEY_POST_MODEL = "RedditDetailViewImpl#arg_key_post_model"

class RedditDetailViewImpl(args: Bundle) : PresentableDaggerController<RedditDetailView, RedditDetailPresenter>(args), RedditDetailView {

    @Inject
    internal lateinit var detailPresenter: RedditDetailPresenter
    internal lateinit var detailComponent: DetailComponent
    private val postModel: RedditPostModel by lazy { this.args.getParcelable<RedditPostModel>(ARG_KEY_POST_MODEL) }

    override val presenter: RedditDetailPresenter
        get() = detailPresenter

    override fun initializeInjector() {
        detailComponent = DaggerDetailComponent.builder()
                .presenterModule(PresenterModule())
                .applicationComponent((activity?.application as MainApplication).applicationComponent)
                .build()
        detailComponent.inject(this)
    }

    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.view_screen_detail, viewGroup, false)

    override fun onFinishInflate(view: View) {
        super.onFinishInflate(view)
        postDetailDraweeView.setImageURI(postModel.postContent)
        postTitleTextView.text = postModel.postTitle
        postAuthorTextView.text = postModel.postAuthor
    }

    companion object {

        fun create(postModel: RedditPostModel): RedditDetailViewImpl {
            val args = Bundle()
            args.putParcelable(ARG_KEY_POST_MODEL, postModel)
            return RedditDetailViewImpl(args)
        }
    }
}
