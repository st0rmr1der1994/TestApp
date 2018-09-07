package com.my.test.testapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork
import com.my.test.testapp.service.network.util.NetworkManager
import io.reactivex.Observable

class NetworkManagerImpl(private val appContext: Context) : NetworkManager {

    override fun isConnected(): Boolean {
        val connectivityManager = appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivityManager.activeNetworkInfo
        return info != null && info.isConnected
    }

    override fun observeConnectedState(): Observable<Boolean> {
        return ReactiveNetwork.observeNetworkConnectivity(appContext)
                .map { connectivity -> connectivity.state() == NetworkInfo.State.CONNECTED }
    }
}
