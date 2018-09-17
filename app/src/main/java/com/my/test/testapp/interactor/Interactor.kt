package com.my.test.testapp.interactor

import io.reactivex.Flowable

abstract class Interactor<E, M> {

    abstract fun interact(metadata: M): Flowable<E>
}
