package com.my.test.testapp.ui.common

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.my.test.testapp.ui.MainActivity

abstract class PresentableDaggerController<V : MvpView, P : MvpPresenter<V>>(args: Bundle? = null) : PresentableController<V, P>(args) {

    protected val activityComponent by lazy { (activity as MainActivity).activityComponent }

    override fun onFinishInflate(view: View) {
        initializeInjector()
    }

    abstract fun initializeInjector()
}
