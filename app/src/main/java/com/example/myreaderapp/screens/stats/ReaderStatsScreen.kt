package com.bawp.freader.screens.stats

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.sharp.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.R
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.model.MBook
import com.example.myreaderapp.screens.home.HomeScreenViewModel
import com.example.myreaderapp.ui.theme.QuickSandTypography
import com.example.myreaderapp.utils.formatDate
import com.google.firebase.auth.FirebaseAuth
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ReaderStatsScreen(navController: NavController,
                      viewModel: HomeScreenViewModel = hiltViewModel()) {

    var books: List<MBook>
    val currentUser = FirebaseAuth.getInstance().currentUser

    Scaffold(
        topBar = {
            ReaderAppBar(title = "Book Stats",
                icon = Icons.Default.ArrowBack,
                showProfile = false,
                navController = navController){
                navController.popBackStack()
            }

        },
    ) {
        Surface(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.abstractt),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.35f
            )
            //only show books by this user that have been read
            books = if (!viewModel.data.value.data.isNullOrEmpty()) {
                viewModel.data.value.data!!.filter { mBook ->
                    (mBook.userId == currentUser?.uid)
                }
            }else {
                emptyList()

            }
            Column {
                Row {
                    Box(modifier = Modifier
                        .size(45.dp)
                        .padding(2.dp)) {
                        Icon(imageVector = Icons.Sharp.Person,
                            contentDescription = "icon" )
                    }
                    //paul @ me.com
                    Text(text = "Hi, ${
                        currentUser?.email.toString().split("@")[0].uppercase(Locale.getDefault())
                    }", style = QuickSandTypography.h4)

                }
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                    shape = RoundedCornerShape(30.dp),
                    backgroundColor = Color(0xE6B6DD9E),
                    elevation = 5.dp
                ) {
                    val readBooksList: List<MBook> = if (!viewModel.data.value.data.isNullOrEmpty()) {
                        books.filter { mBook ->
                            (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                        }

                    }else {
                        emptyList()
                    }

                    val readingBooks = books.filter { mBook ->
                        (mBook.startedReading != null && mBook.finishedReading == null)
                    }

                    Column(modifier = Modifier.padding(start = 25.dp, top = 4.dp, bottom = 4.dp),
                        horizontalAlignment = Alignment.Start) {
                        Text(text = "Your Statistics:", style = QuickSandTypography.h2)
                        Divider()
                        Text(text = "You're reading: ${readingBooks.size} books", style = QuickSandTypography.h4)
                        Text(text = "You've read: ${readBooksList.size} books", style = QuickSandTypography.h4)

                    }

                }

                if (viewModel.data.value.loading == true) {
                    LinearProgressIndicator()
                }else {
                    Divider()
                    LazyColumn(modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                        contentPadding = PaddingValues(16.dp)){
                        //filter books by finished ones
                        val readBooks: List<MBook> = if (!viewModel.data.value.data.isNullOrEmpty()){
                            viewModel.data.value.data!!.filter { mBook ->
                                (mBook.userId == currentUser?.uid) && (mBook.finishedReading != null)
                            }
                        }else {
                            emptyList()

                        }
                        items(items = readBooks) {book ->
                            BookRowStats(book =book )
                        }

                    }
                }

            }





        }

    }

}




@Composable
fun BookRowStats(
    book: MBook) {
    Card(modifier = Modifier
        .clickable {
            //navController.navigate(ReaderScreens.DetailScreen.name + "/${book.id}")
        }
        .fillMaxWidth()
        .height(110.dp)
        .padding(3.dp),
        shape = RectangleShape,
        elevation = 7.dp) {
        Row(modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.Top) {

            val imageUrl: String = if(book.photoUrl.toString().isEmpty())
                "https://images.unsplash.com/photo-1541963463532-d68292c34b19?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=80&q=80"
            else {
                book.photoUrl.toString()
            }
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "book image",
                modifier = Modifier
                    .width(80.dp)
                    .fillMaxHeight()
                    .padding(end = 4.dp),
            )

            Column {

                Row(horizontalArrangement = Arrangement.SpaceBetween) {

                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis, fontFamily= FontFamily(Font(R.font.quicksand_bold, FontWeight.W300)))
                    if (book.rating!! >= 4) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        Icon(imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Thumbs up",
                            tint = Color.Green.copy(alpha = 0.5f))
                    }else {
                        Box{}
                    }
                }
                Text(text =  "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = QuickSandTypography.caption)

                Text(text =  "Started: ${formatDate(book.startedReading!!)}",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style =QuickSandTypography.caption)

                Text(text =  "Finished ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    fontStyle = FontStyle.Italic,
                    style = QuickSandTypography.caption)


            }

        }

    }

}
