package com.my.test.testapp.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class PresentableController<V : MvpView, P : MvpPresenter<V>>(args: Bundle? = null) : Controller(args) {

    val context: Context?
        get() = activity

    abstract val presenter: P

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        return createView(layoutInflater, viewGroup)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.attachView(this as V)
    }


    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.detachView()
    }

    abstract fun createView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View
}
