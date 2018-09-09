package com.my.test.testapp.interactor

import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

abstract class Interactor<E, M> {

    private val disposable: CompositeDisposable = CompositeDisposable()

    protected abstract fun interaction(metadata: M): Single<E>

    fun interact(observer: DisposableSingleObserver<E>, metadata: M) {
        val observable = interaction((metadata))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        addDisposable(observable.subscribeWith(observer))
    }

    fun dispose() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    private fun addDisposable(newDisposable: Disposable) {
        disposable.add(newDisposable)
    }
}
