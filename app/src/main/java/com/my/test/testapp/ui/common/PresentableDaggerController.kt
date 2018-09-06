package com.my.test.testapp.ui.common

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class PresentableDaggerController<V : MvpView, P : MvpPresenter<V>>(args: Bundle? = null) : PresentableController<V, P>(args) {

    override fun createView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val view = inflateView(layoutInflater, viewGroup)
        onFinishInflate(view)
        return view
    }

    protected open fun onFinishInflate(view: View) {
        initializeInjector()
    }

    abstract fun initializeInjector()

    abstract fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View
}
