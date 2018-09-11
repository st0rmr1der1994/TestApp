package com.my.test.testapp.interactor

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DisposableSubscriber

abstract class Interactor<E, M> {

    private val disposable: CompositeDisposable = CompositeDisposable()

    abstract fun interaction(metadata: M): Flowable<E>

    open fun interact(subscriber: DisposableSubscriber<E>, metadata: M) {
        val observable = interaction((metadata))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribeWith(subscriber))
    }

    fun dispose() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    fun addDisposable(newDisposable: Disposable) {
        disposable.add(newDisposable)
    }
}
