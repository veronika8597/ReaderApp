package com.example.myreaderapp.screens.home


import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.myreaderapp.components.FABContent
import com.example.myreaderapp.components.ListCard
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.components.TitleSection
import com.example.myreaderapp.model.MBook
import com.example.myreaderapp.navigation.ReaderScreens
import com.example.myreaderapp.ui.theme.Green700
import com.google.firebase.auth.FirebaseAuth


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()){
    Scaffold(topBar = {
                      ReaderAppBar(title = "BloomReads", navController = navController)

    },
        floatingActionButton = {
            FABContent{
                navController.navigate(ReaderScreens.SearchScreen.name)
            }

        } ) {
        Surface(modifier = Modifier.fillMaxSize()
            ) {
            Image(
                painter = painterResource(id = com.example.myreaderapp.R.drawable.abstractt),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                alpha = 0.35f
            )
                //home content
            HomeContent(navController, viewModel)
        }
    }


}

@Composable
fun HomeContent(navController: NavController, viewModel: HomeScreenViewModel){
    var listOfBooks = emptyList<MBook>()
    val currentUser = FirebaseAuth.getInstance().currentUser

    if (!viewModel.data.value.data.isNullOrEmpty()){
        listOfBooks = viewModel.data.value.data!!.toList().filter { mBook ->
        mBook.userId == currentUser?.uid.toString()
        }
        Log.d("Books", "HomeContent: $listOfBooks")

    }


    val email = FirebaseAuth.getInstance().currentUser?.email
    val currentUserName = if (!email.isNullOrEmpty())
        FirebaseAuth.getInstance().currentUser?.email?.split("@")
            ?.get(0)else
        "N/A"
    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {
        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n " + " activity right now...")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .clickable {
                            navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                        }
                        .size(45.dp),
                    tint = MaterialTheme.colors.primaryVariant)
                Text(text = currentUserName!!,
                    modifier = Modifier.padding(2.dp),
                    style = MaterialTheme.typography.overline,
                    color = Color.Black,
                    fontSize = 15.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Clip)
                Divider()
            }


        }

        ReadingRightNowArea(listOfBooks = listOfBooks,
            navController =navController )
        TitleSection(label = "Reading List")
        BookListArea(listOfBooks = listOfBooks,
            navController = navController)



    }

}

@Composable
fun BookListArea(listOfBooks: List<MBook>,
                 navController: NavController) {
    val addedBooks = listOfBooks.filter { mBook ->
        mBook.startedReading == null && mBook.finishedReading == null
    }



    HorizontalScrollableComponent(addedBooks){
        navController.navigate(ReaderScreens.UpdateScreen.name +"/$it")

    }



}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,
                                  viewModel: HomeScreenViewModel = hiltViewModel(),
                                  onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()

    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)) {
        if (viewModel.data.value.loading == true) {
            LinearProgressIndicator()

        }else {
            if (listOfBooks.isNullOrEmpty()){
                Surface(modifier = Modifier.padding(23.dp)) {
                    Text(text = "No books found. Add a Book",
                        style = TextStyle(
                            color = Color.Red.copy(alpha = 0.4f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    )

                }
            }else {
                for (book in listOfBooks) {
                    ListCard(book) {
                        onCardPressed(book.googleBookId.toString())

                    }
                }
            }

        }



    }


}


@Composable
fun ReadingRightNowArea(listOfBooks: List<MBook>,
                        navController: NavController) {
    //Filter books by reading now
    val readingNowList = listOfBooks.filter { mBook ->
        mBook.startedReading != null && mBook.finishedReading == null
    }

    HorizontalScrollableComponent(readingNowList){
        Log.d("TAG", "BoolListArea: $it")
        navController.navigate(ReaderScreens.UpdateScreen.name + "/$it")
    }

}

