package com.example.snapcart_android_app.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import com.example.domain.model.NetworkState
import com.example.domain.repository.NetworkStatusProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkStatusProviderImplementation(context: Context) : NetworkStatusProvider {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val _networkState = MutableStateFlow(getCurrentNetworkState())
    override val networkState: StateFlow<NetworkState> = _networkState

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            _networkState.value = getCurrentNetworkState()
        }
    }

    init {
        context.registerReceiver(
            networkReceiver,
            IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        )
    }

    private fun getCurrentNetworkState(): NetworkState {
        val activeNetwork = connectivityManager.activeNetworkInfo
        return if (activeNetwork?.isConnectedOrConnecting == true) {
            NetworkState.Connected
        } else {
            NetworkState.Disconnected
        }
    }
}