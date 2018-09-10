package com.my.test.testapp.interactor

import com.my.test.testapp.BaseTest
import io.reactivex.Flowable
import io.reactivex.subscribers.DisposableSubscriber
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class InteractorTest : BaseTest() {

    private var interactor: TestInteractor? = null
    private var testSubscriber: TestDisposableSubscriber<Any>? = null

    @Before
    fun setUp() {
        interactor = TestInteractor()
        testSubscriber = TestDisposableSubscriber()
    }

    @Test
    fun testInteractorReturnCorrectResult() {
        interactor!!.interact(testSubscriber!!, Unit)
        assertThat(testSubscriber!!.valuesCount).isZero()
    }

    @Test
    fun testSubscriptionWhenExecutingUseCase() {
        interactor!!.interact(testSubscriber!!, Unit)
        interactor!!.dispose()

        assertThat(testSubscriber!!.isDisposed).isTrue()
    }

    private class TestInteractor : Interactor<Any, Unit>() {

        override fun interaction(metadata: Unit): Flowable<Any> {
            return Flowable.empty()
        }
    }

    private class TestDisposableSubscriber<T> : DisposableSubscriber<T>() {
        internal var valuesCount = 0

        override fun onNext(value: T) {
            valuesCount++
        }

        override fun onError(e: Throwable) {
            // no-op by default.
        }

        override fun onComplete() {
            // no-op by default.
        }
    }
}

