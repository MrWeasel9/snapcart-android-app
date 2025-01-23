package com.example.domain.model

sealed class NetworkState {
    data object Connected : NetworkState()
    data object Disconnected : NetworkState()
}