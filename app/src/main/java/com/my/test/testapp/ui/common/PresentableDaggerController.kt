package com.my.test.testapp.ui.common

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.my.test.testapp.MainApplication

abstract class PresentableDaggerController<V : MvpView, P : MvpPresenter<V>>(args: Bundle? = null) : PresentableController<V, P>(args) {

    override fun onFinishInflate(view: View) {
        initializeInjector()
    }

    abstract fun initializeInjector()

    fun appComponent() = (activity?.application as MainApplication).applicationComponent
}
