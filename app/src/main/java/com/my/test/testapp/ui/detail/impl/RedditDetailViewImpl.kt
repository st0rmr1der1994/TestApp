package com.my.test.testapp.ui.detail.impl

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.content.ContextCompat.checkSelfPermission
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.my.test.testapp.R
import com.my.test.testapp.di.component.DetailComponent
import com.my.test.testapp.di.module.RedditDetailModule
import com.my.test.testapp.entity.RedditPostModel
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailView
import com.my.test.testapp.ui.detail.util.NotificationProgressView
import kotlinx.android.synthetic.main.view_screen_detail.*
import javax.inject.Inject

private const val ARG_KEY_POST_MODEL = "RedditDetailViewImpl#arg_key_post_model"
private const val PERMISIONS_REQUEST_CODE = 1111

class RedditDetailViewImpl(args: Bundle) : PresentableDaggerController<RedditDetailView, RedditDetailPresenter>(args), RedditDetailView {

    @Inject
    internal lateinit var detailPresenter: RedditDetailPresenter
    @Inject
    lateinit var notificationProgressView: NotificationProgressView

    internal lateinit var detailComponent: DetailComponent
    private val postModel: RedditPostModel by lazy { this.args.getParcelable<RedditPostModel>(ARG_KEY_POST_MODEL) }

    override val presenter: RedditDetailPresenter
        get() = detailPresenter

    override fun initializeInjector() {
        val componentBuilder = activityComponent.detailComponentBuilder()
        componentBuilder.detailModule(RedditDetailModule())
        detailComponent = componentBuilder.build()
        detailComponent.inject(this)
    }

    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.view_screen_detail, viewGroup, false)

    override fun onFinishInflate(view: View) {
        super.onFinishInflate(view)
        postDetailDraweeView.setImageURI(postModel.postContent)
        postTitleTextView.text = postModel.postTitle
        postAuthorTextView.text = postModel.postAuthor
        downloadMediaButton.setOnClickListener { tryLoadContent() }
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        notificationProgressView.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        notificationProgressView.onDetach()
    }

    private fun tryLoadContent() {
        val storagePermissionLabel = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        if (checkSelfPermission(context!!, storagePermissionLabel)
                == PackageManager.PERMISSION_GRANTED) {
            presenter.loadContent(postModel.postContent)
        } else {
            requestPermissions(arrayOf(storagePermissionLabel), PERMISIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(grantResults[0]== PackageManager.PERMISSION_GRANTED) {
            tryLoadContent()
        }
    }

    override fun showLoading() = notificationProgressView.show()

    override fun hideLoading() = notificationProgressView.hide()

    override fun showResult(result: String) {
        Toast.makeText(context, context!!.getString(R.string.toast_downloaded_prefix, result), Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun create(postModel: RedditPostModel): RedditDetailViewImpl {
            val args = Bundle()
            args.putParcelable(ARG_KEY_POST_MODEL, postModel)
            return RedditDetailViewImpl(args)
        }
    }
}
