package com.example.myreaderapp.screens.login

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myreaderapp.R
import com.example.myreaderapp.components.EmailInput
import com.example.myreaderapp.components.PasswordInput
import com.example.myreaderapp.components.ReaderLogo
import com.example.myreaderapp.navigation.ReaderScreens

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReaderLoginScreen(navController: NavController,
                      viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel()){

    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    Surface(modifier = Modifier.fillMaxSize())
        {
            Image(
                painter = painterResource(id = R.drawable.abstractt),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

        Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top) {
            ReaderLogo()
            if(showLoginForm.value) UserForm(loading = false, isCreatedAccount = false) { email, password ->

                viewModel.signInWithEmailAndPassword(email, password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }
            else
                {
                    UserForm(loading = false , isCreatedAccount = true) { email, password ->
                        viewModel.createUserWithEmailAndPassword(email, password){
                            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                        }

                    }
                }


        }
            Spacer(modifier = Modifier.height(25.dp))
            Row(modifier = Modifier.padding(25.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically)
            {
                val text = if(showLoginForm.value) "Sign up" else "Login"
                Text(text = "New User?")
                Text(text,
                    modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                      fontWeight = FontWeight.Bold,
                      color = MaterialTheme.colors.primaryVariant)
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

        if(isCreatedAccount) Text(text = stringResource(id = R.string.create_acct),
                                    modifier = Modifier.padding(4.dp))
        else Text("")

        EmailInput(emailState = email, enabled = !loading, onAction = KeyboardActions {
            passwordFocusRequest.requestFocus()
        })

        PasswordInput(modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            lableId= "Password",
            enabled = !loading,
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions{
                if (!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            })

        SubmitButton(
            textId = if (isCreatedAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }
    }

}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        enabled = !loading && validInputs,
        shape = CircleShape,
    ) {
        if (loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }

}


