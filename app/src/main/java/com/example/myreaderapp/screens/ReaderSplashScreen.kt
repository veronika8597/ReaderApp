package com.example.myreaderapp.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myreaderapp.R
import com.example.myreaderapp.components.ReaderLogo
import com.example.myreaderapp.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@Composable
fun ReaderSplashScreen(navController: NavController){

    //The actual animation
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
                        animationSpec = tween(durationMillis = 800,
                        easing = {
                            OvershootInterpolator(8f)
                                .getInterpolation(it)
                        })
        )
        delay(2000L)

        //Checks if there's a FireBase user, if so - takes them to Home screen, otherwise, to Login Screen
        if(FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(ReaderScreens.LoginScreen.name)
        }else{
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }

        //navController.navigate(ReaderScreens.LoginScreen.name)
    }

    //Logo
    Surface(modifier = Modifier
        .padding(15.dp)
        .size(380.dp)
        .scale(scale.value),
        shape = CircleShape,
        color = Color.White,
       ){

        // border = BorderStroke(width = 2.dp, Color.LightGray)
        Image(
            painter = painterResource(id = R.drawable.girlreading),
            contentDescription = null,
            modifier = Modifier.fillMaxSize().alpha(0.5f),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.padding(1.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ReaderLogo()
            Spacer(modifier = Modifier.heightIn(15.dp))
            Text(text = "Read. Explore. Enrich.",
            style = MaterialTheme.typography.h5,
            color = Color.LightGray)
        }
    }

}
