package com.example.technicaltest2.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.technicaltest2.AuthState
import com.example.technicaltest2.AuthViewModel
import com.example.technicaltest2.R
import com.example.technicaltest2.data.local.StudentData
import com.example.technicaltest2.data.model.Student
import com.example.technicaltest2.ui.theme.PrimaryColor
import com.example.technicaltest2.ui.theme.SecondaryColor

@Composable
fun StudentListPage(modifier: Modifier, navController: NavController, authViewModel: AuthViewModel) {
    // authentication
    val authState = authViewModel.authState.observeAsState()
    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> { Unit }
        }
    }

    // animation
    val isColumnVisible = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        isColumnVisible.value = true
    }

    // name search
    var nameSearch by remember { mutableStateOf("") }

    //logout window
    var isLogoutVisible by remember { mutableStateOf(false) }

    // student detail
    var openedStudent by remember { mutableStateOf<Student?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.3f))
            .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
            .zIndex(if (isLogoutVisible) 2f else 0f)
            .clickable(enabled = true, onClick = { isLogoutVisible = false })
    ){
        AnimatedVisibility(
            visible = isLogoutVisible,
            enter = fadeIn(
                animationSpec = tween(durationMillis = 600)
            ),
            exit = fadeOut(
                animationSpec = tween(durationMillis = 1000)
            ),
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White)
                    .align(Alignment.Center)
                    .padding(vertical = 30.dp, horizontal = 24.dp)
            ){
                Column {
                    Text("Are you sure you want to logout?", fontSize = 24.sp, color = SecondaryColor, fontWeight = FontWeight.Bold, textAlign = TextAlign.Center)
                    Spacer(modifier = Modifier.weight(1f))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = {
                                isLogoutVisible = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = SecondaryColor
                            ),
                            modifier = Modifier
                                .width(110.dp)
                                .height(50.dp)
                        ) {
                            Text("Cancel", color = Color.White)
                        }
                        Button(
                            onClick = {
                                authViewModel.logout()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = PrimaryColor
                            ),
                            modifier = Modifier
                                .width(110.dp)
                                .height(50.dp)
                        ) {
                            Text("Logout", color = Color.White)
                        }
                    }
                }
            }
        }

    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Transparent)
            .zIndex(1f)
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ){
            IconButton(
                onClick = {
                    isLogoutVisible = true
                }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout",
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(PrimaryColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AnimatedVisibility(
            visible = isColumnVisible.value,
            enter = slideInHorizontally(
                initialOffsetX = { fullHeight -> fullHeight},
                animationSpec = tween(durationMillis = 1000)
            )
        ){
            Image(
                painter = painterResource(id = R.drawable.alfagift),
                contentDescription = "alfagift",
                modifier = Modifier.size(70.dp)
            )
        }
        AnimatedVisibility(
            visible = isColumnVisible.value,
            enter = slideInVertically(
                initialOffsetY = { fullHeight -> fullHeight },
                animationSpec = tween(durationMillis = 1000)
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp))
                    .shadow(
                        elevation = 20.dp,
                        shape = RoundedCornerShape(topStart = 48.dp, topEnd = 48.dp),
                        clip = true
                    )
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "Student",
                        color = PrimaryColor,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = " List",
                        color = SecondaryColor,
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                TextField(
                    value = nameSearch,
                    onValueChange = {
                        nameSearch = it
                    },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.White,
                        unfocusedLabelColor = SecondaryColor,
                        unfocusedTextColor = Color.Black,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedContainerColor = Color.White,
                        focusedLabelColor = SecondaryColor,
                        focusedTextColor = Color.Black,
                        focusedIndicatorColor = Color.Transparent,
                        cursorColor = Color.White,
                    ),
                    label ={
                        Text(
                            text = "Search by name",
                            color = SecondaryColor,
                            fontSize = 14.sp
                        )
                    },
                    shape = RoundedCornerShape(16.dp),
                    trailingIcon = {
                        Image(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(24.dp),
                            colorFilter = ColorFilter.tint(SecondaryColor)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .border(3.dp, SecondaryColor, shape = RoundedCornerShape(16.dp))
                )
                Spacer(modifier = Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(start = 8.dp, end = 8.dp, bottom = 20.dp)
                ) {
                    val filteredStudents = StudentData.students.filter { student ->
                        student.name.contains(nameSearch, ignoreCase = true)
                    }
                    items(filteredStudents) { student ->
                        StudentItem(student, openedStudent) { openedStudent = it }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentItem(student: Student, openedStudent: Student?, onStudentClick: (Student?) -> Unit) {
    val isOpened = student == openedStudent
    val height by animateDpAsState(targetValue = if (isOpened) 200.dp else 100.dp, animationSpec = tween(durationMillis = 300))
    val addressFontSize by animateFloatAsState(targetValue = if (isOpened) 14f else 18f, animationSpec = tween(durationMillis = 300))
    val fontSize by animateFloatAsState(targetValue = if (isOpened) 12f else 0f, animationSpec = tween(durationMillis = 300))
    val rotationAngle by animateFloatAsState(targetValue = if (isOpened) 180f else 0f, animationSpec = tween(durationMillis = 300))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(if (isOpened) 20.dp else 5.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = student.profilePictureUrl),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .fillMaxHeight(0.75f)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .fillMaxHeight(0.8f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(student.name, fontSize = 22.sp, color = SecondaryColor, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(student.address, color = Color.Black.copy(0.6f), fontSize = addressFontSize.sp)
                if (isOpened) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text("Age: ${student.age}", color = Color.Black.copy(0.6f), fontSize = fontSize.sp)
                    Text("GPA: ${student.gpa}", color = Color.Black.copy(0.6f), fontSize = fontSize.sp)
                    Text("\"${student.quote}\"", color = Color.Black.copy(0.6f), fontSize = fontSize.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onStudentClick(if (isOpened) null else student)
                },
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "info",
                    tint = SecondaryColor,
                    modifier = Modifier.rotate(rotationAngle)
                )
            }
        }
    }
}