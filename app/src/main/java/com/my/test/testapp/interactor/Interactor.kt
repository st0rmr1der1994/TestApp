package com.my.test.testapp.interactor

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

abstract class Interactor<E, M> {

    private val disposable: CompositeDisposable = CompositeDisposable()

    protected abstract fun interactingObservable(metadata: M): Observable<E>

    fun interact(observer: DisposableObserver<E>, metadata: M) {
        val observable = interactingObservable((metadata))
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
