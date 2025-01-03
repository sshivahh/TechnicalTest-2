package com.example.technicaltest2.pages

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.navigation.NavController
import com.example.technicaltest2.AuthState
import com.example.technicaltest2.AuthViewModel

@Composable
fun LoginPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel){
    val authState = authViewModel.authState.observeAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> {
                navController.navigate("list")
            }
            is AuthState.Error -> {
                Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
                authViewModel.resetAuthState()
            }
            else -> { Unit }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Login Page")
        Column (
            modifier = Modifier.fillMaxWidth(0.5f),
        ){
            TextField(
                value = username,
                onValueChange = {
                    username = it
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Username") }
            )
            TextField(
                value = password,
                onValueChange = {
                    password = it
                },
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = PasswordVisualTransformation(),
                label = { Text("Password") }
            )
            Button(
                onClick = {
                    authViewModel.login(username, password)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = authState.value !is AuthState.Loading
            ) {
                Text("Login")
            }
        }
    }
}