package com.my.test.testapp.utils

import io.reactivex.Observable

interface NetworkManager {
    fun isConnected(): Boolean

    fun observeConnectedState(): Observable<Boolean>
}
