package com.my.test.testapp.service.network.util

import io.reactivex.Observable

interface NetworkManager {
    fun isConnected(): Boolean

    fun observeConnectedState(): Observable<Boolean>
}
