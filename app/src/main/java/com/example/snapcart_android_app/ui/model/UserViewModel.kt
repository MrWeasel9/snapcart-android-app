package com.example.snapcart_android_app.ui.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {


    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    val userState: StateFlow<UserState> = _userState


    init {
        fetchCurrentUser()
    }

    private fun fetchCurrentUser() {
        viewModelScope.launch {
            val firebaseUser = FirebaseAuth.getInstance().currentUser
            if (firebaseUser != null) {
                _userState.value = UserState.Authenticated(firebaseUser)
            } else {
                _userState.value = UserState.Unauthenticated
            }
        }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        _userState.value = UserState.Unauthenticated
    }

    sealed class UserState {
        data object Loading : UserState()
        data object Unauthenticated : UserState()
        data class Authenticated(val user: com.google.firebase.auth.FirebaseUser) : UserState()
    }
}