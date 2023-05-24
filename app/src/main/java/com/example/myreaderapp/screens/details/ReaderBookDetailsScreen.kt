package com.example.myreaderapp.screens.details

import android.annotation.SuppressLint
import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.data.Resource
import com.example.myreaderapp.model.Item
import com.example.myreaderapp.navigation.ReaderScreens
import com.example.myreaderapp.ui.theme.Coral500
import org.w3c.dom.Text

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun BookDetailsScreen(navController: NavController,
                      bookId: String,
                      viewModel: DetailsViewModel = hiltViewModel()) {

    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book Details",
            icon = Icons.Default.ArrowBack,
            showProfile = false,
            navController = navController
        ) {
            navController.navigate(ReaderScreens.SearchScreen.name)
        }
    }) {
        Surface(
            modifier = Modifier
                .padding(3.dp)
                .fillMaxSize()) {
            Column(
                modifier = Modifier.padding(top = 12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally) {

                val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()){

                    value = viewModel.getBookInfo(bookId)
                }.value

                if (bookInfo.data == null) {
                    Row() {
                        LinearProgressIndicator()
                        Text("Loading...")
                    }

                } else {
                    ShowBookDetails(bookInfo, navController)
                }
            }
        }
    }
}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id


    Card(
        modifier = Modifier.padding(34.dp),
        shape = RectangleShape, elevation = 4.dp
    ) {
        Image(
            painter = rememberImagePainter(data = bookData!!.imageLinks.thumbnail),
            contentDescription = "Book Image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }

    Text(
        text = bookData?.title.toString(),
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19
    )

    Text(text = "Authors: ${bookData?.authors.toString()}")
    Text(text = "Page Count: ${bookData?.pageCount.toString()}")
    Text(
        text = "Categories: ${bookData?.categories.toString()}",
        style = MaterialTheme.typography.subtitle1,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = "Published: ${bookData?.publishedDate.toString()}",
        style = MaterialTheme.typography.subtitle1
    )

    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription = HtmlCompat.fromHtml(bookData!!.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY).toString()

    val localDims = LocalContext.current.resources.displayMetrics

    Surface(modifier = Modifier
        .height(localDims.heightPixels.dp.times(0.09f))
        .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Coral500)
    ) {

            LazyColumn(modifier = Modifier.padding(3.dp)) {
                item {
                    Text(text = cleanDescription)
                }
            }
    }
}