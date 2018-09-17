package com.my.test.testapp.ui.common

import com.hannesdorfmann.mosby3.mvp.MvpNullObjectBasePresenter
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class MvpPresenterImpl<V : MvpView> : MvpPresenter<V>, MvpNullObjectBasePresenter<V>() {

    private val disposable: CompositeDisposable = CompositeDisposable()


    fun dispose() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun addDisposable(newDisposable: Disposable) {
        disposable.add(newDisposable)
    }
}
