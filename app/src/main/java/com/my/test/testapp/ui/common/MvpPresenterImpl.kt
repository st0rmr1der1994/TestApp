package com.my.test.testapp.ui.common

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView

open class MvpPresenterImpl<V : MvpView> : MvpPresenter<V>, MvpNullObjectBasePresenter<V>()
