package com.example.technicaltest2

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.technicaltest2.pages.LoginPage
import com.example.technicaltest2.pages.StudentListPage


@Composable
fun Navigation(modifier:Modifier = Modifier, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login", builder = {
        composable("login") {
            LoginPage(modifier, navController, authViewModel)
        }
        composable("list") {
            StudentListPage(modifier, navController, authViewModel)
        }
    })
}