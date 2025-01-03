package com.example.technicaltest2.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.layout.offset
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
import androidx.compose.ui.res.painterResource
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
fun LoginPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // sliding animation
    val isImageVisible = remember { mutableStateOf(false) }
    val isColumnVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isImageVisible.value = true
        delay(1400)
        isColumnVisible.value = true
    }

    // error message
    var errorMessage by remember { mutableStateOf("") }

    // auth state
    LaunchedEffect(authState.value) {
        when (authState.value) {
            is AuthState.Authenticated -> {
                navController.navigate("list")
            }
            is AuthState.Error -> {
                errorMessage = (authState.value as AuthState.Error).message
                authViewModel.resetAuthState()
            }
            else -> { Unit }
        }
    }

    // password visibility
    var isPasswordVisible by remember { mutableStateOf(false) }

    // animate image position
    val imageOffsetY by animateFloatAsState(
        targetValue = if (isColumnVisible.value) -10f else 0f,
        animationSpec = tween(durationMillis = 600)
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryColor),
        contentAlignment = Alignment.BottomEnd
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(PrimaryColor),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnimatedVisibility(
                visible = isImageVisible.value,
                enter = fadeIn(
                    animationSpec = tween(
                        durationMillis = 500,
                        easing = EaseIn
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.3f)
                        .offset(y = imageOffsetY.dp),
                    contentAlignment = Alignment.Center
                ) {
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
            ) {
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
                    Row(
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
                                focusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
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
                    Row(
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
                                focusedContainerColor = Color.White,
                                focusedTextColor = Color.Black,
                                unfocusedTextColor = Color.Black,
                                cursorColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth(0.7f)
                                .clip(RoundedCornerShape(10.dp)),
                            label = {
                                Text("Password")
                            },
                            visualTransformation = if (!isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
                            trailingIcon = {
                                IconButton(
                                    onClick = {
                                        isPasswordVisible = !isPasswordVisible
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
                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = errorMessage,
                        color = Color.Red,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(14.dp))
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(40.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
        }
    }
}