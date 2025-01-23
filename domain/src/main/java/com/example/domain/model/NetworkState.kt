package com.example.domain.model

sealed class NetworkState {
    object Connected : NetworkState()
    object Disconnected : NetworkState()
}