package com.my.test.testapp.ui.common

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bluelinelabs.conductor.Controller
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.clearFindViewByIdCache

abstract class BaseController(args: Bundle? = null)
    : Controller(args), LayoutContainer {

    val context: Context?
        get() = activity

    private var  _containerView: View? = null
    override val containerView: View?
        get() = _containerView

    override fun onCreateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View {
        val view = inflateView(layoutInflater, viewGroup)
        _containerView = view
        onFinishInflate(view)
        return view
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        clearFindViewByIdCache()
        _containerView = null
    }

    abstract fun inflateView(layoutInflater: LayoutInflater, viewGroup: ViewGroup): View

    abstract fun onFinishInflate(view: View)
}
