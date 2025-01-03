package com.example.technicaltest2

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AuthViewModel : ViewModel(){

    private val _authState = MutableLiveData<AuthState>().apply {
        value = AuthState.Authenticated
    }
    val authState : LiveData<AuthState> = _authState

    fun login(username: String, password: String){
        _authState.value = AuthState.Loading

        if(username.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Please fill the username and passwrod")
            return
        }

        if(username == "alfagift-admin" && password == "asdf"){
            _authState.value = AuthState.Authenticated
        }else{
            _authState.value = AuthState.Error("Invalid username or password")
        }
    }

    fun resetAuthState() {
        _authState.value = AuthState.Unauthenticated
    }


}

sealed class AuthState {
    data object Authenticated : AuthState()
    data object Unauthenticated : AuthState()
    data object Loading: AuthState()
    data class Error(val message: String) : AuthState()
}