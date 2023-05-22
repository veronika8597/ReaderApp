package com.example.myreaderapp.screens.search

import android.annotation.SuppressLint
import android.util.Log
import android.util.MonthDisplayHelper
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.components.InputField
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.model.MBook
import com.example.myreaderapp.navigation.ReaderScreens

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReaderSearchScreen(navController: NavController){
    Scaffold(topBar = {
        ReaderAppBar(title = "Search Books",
            icon = Icons.Default.ArrowBack,
            navController = navController,
            showProfile = false){
            navController.navigate(ReaderScreens.ReaderHomeScreen.name)
        }
    }) {
        Surface() {
            Column {
                SearchForm(modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)){
                    Log.d("TAG", "SearchScreen: $it")
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController)
            }
            
        }

    }

}

@Composable
fun BookList(navController: NavController) {

    val listOfBooks = listOf(
        MBook(id = "hfsdjkfhj", title = "Hello Again", authors = "All of us",notes = null),
        MBook(id = "hfsdjkfhj", title = "Hello ", authors = "All of us",notes = null),
        MBook(id = "hfsdjkfhj", title = " Again", authors = "All of us",notes = null),
        MBook(id = "hfsdjkfhj", title = "Hello Again", authors = "All of us",notes = null)
    )

    LazyColumn(modifier = Modifier.fillMaxSize(),
    contentPadding = PaddingValues(16.dp)){
        items(items = listOfBooks){book ->
            BookRow(book, navController)

        }

    }

}

@Composable
fun BookRow(book: MBook, navController: NavController) {
    Card(modifier = Modifier
        .clickable { }
        .fillMaxWidth()
        .height(100.dp)
        .padding(3.dp),
    shape = RectangleShape,
    elevation = 7.dp) {
        Row(modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.Top) {
            val imageUrl = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbTX0mvWV40iwuM5P_deNJfg16J-MmUTMQCyVl2KEIkQ&s"
            Image(painter = rememberImagePainter(data = imageUrl), contentDescription = "bookImage",
            modifier = Modifier.width(80.dp).fillMaxHeight().padding(end = 4.dp))
            Column() {
                Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                Text(text ="Author: ${book.authors}", overflow = TextOverflow.Clip,
                style = MaterialTheme.typography.caption)
                //Text(text = book.id.toString(), overflow = TextOverflow.Ellipsis)

                
            }
            
        }

    }

}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(modifier: Modifier = Modifier,
               loading: Boolean = false,
               hint: String = "Search",
               onSearch: (String) -> Unit = {}){
    Column() {
        val searchQueryState = rememberSaveable{mutableStateOf("")}
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }
        InputField(valueState = searchQueryState, lableId = "Search", enabled = true,
        onAction = KeyboardActions{
            if(!valid) return@KeyboardActions
            onSearch(searchQueryState.value.trim())
            searchQueryState.value = ""
            keyboardController?.hide()
        }
        )
        
    }
    
}