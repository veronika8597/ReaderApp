package com.example.myreaderapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myreaderapp.navigation.ReaderNavigation
import com.example.myreaderapp.ui.theme.MyReaderAppTheme
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReaderApp()
        }
    }
}

@Composable
fun ReaderApp(){

    MyReaderAppTheme {

        Surface(color = MaterialTheme.colors.background,
            modifier = Modifier.fillMaxSize()){
            Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
                ReaderNavigation()
                
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyReaderAppTheme {
    }
}