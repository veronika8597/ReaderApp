package com.example.myreaderapp.screens.search

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.R
import com.example.myreaderapp.components.InputField
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.model.Item
import com.example.myreaderapp.navigation.ReaderScreens
import com.example.myreaderapp.ui.theme.QuickSandTypography

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReaderSearchScreen(navController: NavController,
                       viewModel: BookSearchViewModel = hiltViewModel()){

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
                    .padding(16.dp)){ searchQuery ->
                    viewModel.searchBooks(query = searchQuery)
                }
                Spacer(modifier = Modifier.height(13.dp))
                BookList(navController = navController)
            }
            
        }

    }

}

@Composable
fun BookList(navController: NavController,
             viewModel: BookSearchViewModel = hiltViewModel()) {

    val listOfBooks = viewModel.list

    Surface(modifier = Modifier.fillMaxSize())
    {
        Image(
            painter = painterResource(id = R.drawable.abstractt),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            alpha = 0.35f
        )

        if (viewModel.isLoading) {
            Row(
                modifier = Modifier.padding(end = 2.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LinearProgressIndicator()
                Text(text = "Loading...")
            }

        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(items = listOfBooks) { book ->
                    BookRow(book, navController)

                }

            }
        }
    }

}

@Composable
fun BookRow(book: Item, navController: NavController) {
    Card(modifier = Modifier
        .clickable {
            navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(115.dp)
        .padding(3.dp),
    shape = RoundedCornerShape(10.dp),
        backgroundColor = Color.White.copy(alpha = 0.85f),

    elevation = 7.dp) {
        Row(modifier = Modifier.padding(5.dp),
        verticalAlignment = Alignment.Top) {

            val imageUrl: String = if(book.volumeInfo.imageLinks?.smallThumbnail.isNullOrEmpty()) { "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80" }
            else{
                book.volumeInfo.imageLinks!!.smallThumbnail
            }

            Image(painter = rememberImagePainter(data = imageUrl), contentDescription = "bookImage",
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()
                .padding(end = 4.dp))

            Column() {
                Text(text = book.volumeInfo.title, overflow = TextOverflow.Ellipsis, style = QuickSandTypography.h4)
                Text(text =  "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = QuickSandTypography.caption)

                Text(text =  "Date: ${book.volumeInfo.publishedDate}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = QuickSandTypography.caption)

                Text(text =  "${book.volumeInfo.categories}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = QuickSandTypography.caption)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchForm(modifier: Modifier = Modifier,
               //viewModel: BookSearchViewModel,
               loading: Boolean = false,
               hint: String = "Search",
               onSearch: (String) -> Unit = {}){
    Column() {
        val searchQueryState = rememberSaveable{mutableStateOf("")}
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchQueryState.value){
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(valueState = searchQueryState,
            lableId = "Search",
            enabled = true,
            onAction = KeyboardActions{
            if(!valid) return@KeyboardActions
            onSearch(searchQueryState.value.trim())
            searchQueryState.value = ""
            keyboardController?.hide()
        })
    }
}