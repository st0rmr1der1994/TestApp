package com.my.test.testapp.ui.detail.impl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.my.test.testapp.R
import com.my.test.testapp.di.component.DaggerDetailComponent
import com.my.test.testapp.di.component.DetailComponent
import com.my.test.testapp.di.module.PresenterModule
import com.my.test.testapp.ui.common.PresentableDaggerController
import com.my.test.testapp.ui.detail.RedditDetailPresenter
import com.my.test.testapp.ui.detail.RedditDetailView
import javax.inject.Inject

class RedditDetailViewImpl : PresentableDaggerController<RedditDetailView, RedditDetailPresenter>(), RedditDetailView {

    @Inject
    internal lateinit var detailPresenter: RedditDetailPresenter
    internal lateinit var detailComponent: DetailComponent

    override val presenter: RedditDetailPresenter
        get() = detailPresenter

    override fun initializeInjector() {
        detailComponent = DaggerDetailComponent.builder()
                .presenterModule(PresenterModule())
                .build()
        detailComponent.inject(this)
    }


    override fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View =
            layoutInflater.inflate(R.layout.activity_main, viewGroup, false)
}
