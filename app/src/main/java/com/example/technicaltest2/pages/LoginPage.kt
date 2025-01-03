package com.example.technicaltest2.pages

import android.widget.Space
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.technicaltest2.AuthState
import com.example.technicaltest2.AuthViewModel
import com.example.technicaltest2.R
import com.example.technicaltest2.ui.theme.PrimaryColor
import com.example.technicaltest2.ui.theme.SecondaryColor
import kotlinx.coroutines.delay

@Composable
fun LoginPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel){
    val authState = authViewModel.authState.observeAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    //sliding animation
    val isImageVisible = remember { mutableStateOf(false) }
    val isColumnVisible = remember { mutableStateOf(false) }
    val isErrorVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isColumnVisible.value = true
        delay(250)
        isImageVisible.value = true
    }

    //error message
    var errorMessage by remember { mutableStateOf("") }
    suspend fun showErrorMsg(){
        isErrorVisible.value = true
        delay(3000)
        isErrorVisible.value = false
    }

    //auth state
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> {
                navController.navigate("list")
            }
            is AuthState.Error -> {
                errorMessage = (authState.value as AuthState.Error).message
                showErrorMsg()
                authViewModel.resetAuthState()
            }
            else -> { Unit }
        }
    }

    //password visibility
    var isPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.BottomCenter
    ){
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PrimaryColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isImageVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = EaseIn
                    )
                )
            ){
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f),
                    contentAlignment = Alignment.Center
                ){

                    Image(
                        painter = painterResource(id = R.drawable.alfagift),
                        contentDescription = "Alfagift Logo",
                        modifier = Modifier.size(300.dp)
                    )
                }
            }
            AnimatedVisibility(
                visible = isColumnVisible.value,
                enter = slideInVertically(
                    initialOffsetY = { fullHeight -> fullHeight },
                    animationSpec = tween(durationMillis = 600)
                )
            ){
                Column(
                    modifier = Modifier
                        .shadow(
                            elevation = 20.dp,
                            shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                            clip = true
                        )
                        .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                        .background(Color.White)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(48.dp))
                    Row {
                        Text("Log In", fontSize = 24.sp, color = SecondaryColor, fontWeight = FontWeight.Bold)
                        Text(" to your account.", fontSize = 24.sp, color = Color.Gray)
                    }
                    Spacer(modifier = Modifier.height(48.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Icons.Default.Person,
                            contentDescription = "username",
                            colorFilter = ColorFilter.tint(SecondaryColor),
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = username,
                            onValueChange = {
                                username = it
                            },
                            colors = TextFieldDefaults.colors(
                                focusedLabelColor = SecondaryColor,
                                focusedIndicatorColor = SecondaryColor,
                                unfocusedLabelColor = SecondaryColor,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(10.dp)),
                            label = {
                                Text("Username")
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Row (
                        modifier = Modifier.fillMaxWidth(0.9f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            imageVector = Icons.Default.Password,
                            contentDescription = "password",
                            colorFilter = ColorFilter.tint(SecondaryColor),
                            modifier = Modifier.size(30.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        TextField(
                            value = password,
                            onValueChange = {
                                password = it
                            },
                            colors = TextFieldDefaults.colors(
                                focusedLabelColor = SecondaryColor,
                                focusedIndicatorColor = SecondaryColor,
                                unfocusedLabelColor = SecondaryColor,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(10.dp)),
                            label = {
                                Text("Password")
                            },
                            visualTransformation = if(!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        if(isPasswordVisible){
                                            isPasswordVisible = false
                                        } else {
                                            isPasswordVisible = true
                                        }
                                    },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                        contentDescription = "visibility",
                                        tint = SecondaryColor
                                    )
                                }
                            }
                        )
                    }
                    Spacer(modifier = Modifier.height(36.dp))
                    Button(
                        onClick = {
                            authViewModel.login(username, password)
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.6f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = SecondaryColor,
                            contentColor = Color.White
                        ),
                        enabled = authState.value !is AuthState.Loading
                    ) {
                        Text("Login")
                    }
                }
            }
        }
        AnimatedVisibility(
            visible = isErrorVisible.value,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 200,
                    easing = EaseOut
                )
            ),
            exit = slideOutVertically(
                targetOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(
                    durationMillis = 200,
                    easing = EaseIn
                )
            )
        ){
            Row(
                modifier = Modifier
                    .width(250.dp)
                    .height(150.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Top
            ){
                Text(errorMessage, color = Color.Red.copy(0.9f), modifier = Modifier.fillMaxWidth(0.5f), fontSize = 14.sp, textAlign = TextAlign.End, lineHeight = 16.sp)
                Image(
                    painter = painterResource(id = R.drawable.salma),
                    contentDescription = "Background image",
                    modifier = Modifier.size(200.dp)
                )
            }
        }
    }
}