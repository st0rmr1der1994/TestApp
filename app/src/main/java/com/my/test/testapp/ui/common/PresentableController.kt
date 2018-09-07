package com.my.test.testapp.ui.common

import android.os.Bundle
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

abstract class PresentableController<V : MvpView, P : MvpPresenter<V>>(args: Bundle? = null) : BaseController(args) {

    abstract val presenter: P

    @Suppress("UNCHECKED_CAST")
    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.attachView(this as V)
    }


    override fun onDetach(view: View) {
        super.onDetach(view)
        presenter.detachView()
    }
}
