package com.example.domain.repository

import com.example.domain.model.NetworkState
import kotlinx.coroutines.flow.Flow

interface NetworkStatusProvider {
    val networkState: Flow<NetworkState>
}