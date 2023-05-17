package com.example.myreaderapp.screens.login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myreaderapp.components.EmailInput
import com.example.myreaderapp.components.PasswordInput
import com.example.myreaderapp.components.ReaderLogo

@Composable
fun ReaderLoginScreen(navController: NavController){

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
            ReaderLogo()
            UserForm(loading = false, isCreatedAccount = false){ email, password ->
                Log.d("Form" , "ReaderLoginScreen: $email $password")
            }

        }

    }

}


@OptIn(ExperimentalComposeUiApi::class)
@Preview
@Composable
fun UserForm(loading: Boolean = false,
             isCreatedAccount: Boolean = false,
             onDone: (String, String) -> Unit = {email, pwd -> }){

    val email = rememberSaveable{ mutableStateOf("")}
    val password = rememberSaveable{mutableStateOf("")}
    val passwordVisibility = rememberSaveable{mutableStateOf(false)}
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value){
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })
    }

    PasswordInput(modifier = Modifier.focusRequester(passwordFocusRequest),
    passwordState = password,
        lableId= "Password",
        enabled = !loading,
        passwordVisibility = passwordVisibility,
        onAction = KeyboardActions{
            if (!valid) return@KeyboardActions
            onDone(email.value.trim(), password.value.trim())
        })

}

