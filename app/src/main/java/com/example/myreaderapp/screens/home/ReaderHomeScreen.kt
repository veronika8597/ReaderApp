package com.example.myreaderapp.screens.home


import android.nfc.Tag
import android.telecom.Call.Details
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.ReaderApplication
import com.example.myreaderapp.components.BookRating
import com.example.myreaderapp.components.FABContent
import com.example.myreaderapp.components.ListCard
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.components.TitleSection
import com.example.myreaderapp.model.MBook
import com.example.myreaderapp.navigation.ReaderScreens
import com.google.firebase.R
import com.google.firebase.auth.FirebaseAuth
import com.google.rpc.RetryInfo


@Preview
@Composable
fun Home(navController: NavController = NavController(LocalContext.current)){
    Scaffold(topBar = {
                      ReaderAppBar(title = "MY BOOK", navController = navController)

    },
        floatingActionButton = {
            FABContent{}


        } ) {


        Surface(modifier = Modifier.fillMaxSize()) {
                //home content
            HomeContent(navController)
        }
    }


}

@Composable
fun HomeContent(navController: NavController){

    val listOfBooks = listOf(MBook(id = "hfsdjkfhj", title = "Hello Again", authors = "All of us",notes = null),
        MBook(id = "hfsdjkfhj", title = "Hello ", authors = "All of us",notes = null),
        MBook(id = "hfsdjkfhj", title = " Again", authors = "All of us",notes = null),
        MBook(id = "hfsdjkfhj", title = "Hello Again", authors = "All of us",notes = null))

    Column(Modifier.padding(2.dp),
        verticalArrangement = Arrangement.Top) {

        val currentUserName = if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty())
            FirebaseAuth.getInstance().currentUser?.email?.split("@")?.get(0)else
                "N/A"

        Row(modifier = Modifier.align(alignment = Alignment.Start)) {
            TitleSection(label = "Your reading \n "+" activity right now")
            Spacer(modifier = Modifier.fillMaxWidth(0.7f))
            Column {
                Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "Profile", modifier = Modifier
                    .clickable {
                        navController.navigate(ReaderScreens.ReaderStatsScreen.name)
                    }
                    .size(45.dp), tint = MaterialTheme.colors.secondaryVariant)
                Text(text = currentUserName!!, modifier = Modifier.padding(2.dp), style = MaterialTheme.typography.overline, color = Color.Red, fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Clip)
                Divider()
            }

        }

        ReadingRightNowArea(books = listOf(), navController = navController)

        TitleSection(label = "reading List")

        BoolListArea(listOfBooks = listOfBooks, navController = navController)
        
    }


}

@Composable
fun BoolListArea(listOfBooks: List<MBook>, navController: NavController) {

    HorizontalScrollableComponent(listOfBooks){
        Log.d("TAG", "BoolListArea: $it")
        //Todo: on card clicked go to details
    }

}

@Composable
fun HorizontalScrollableComponent(listOfBooks: List<MBook>,onCardPressed: (String) -> Unit) {
    val scrollState = rememberScrollState()
    
    Row(modifier = Modifier
        .fillMaxWidth()
        .heightIn(280.dp)
        .horizontalScroll(scrollState)) {

        for (book in listOfBooks){
            ListCard(book){
                onCardPressed(it)
            }

        }
        
    }

}


@Composable
fun ReadingRightNowArea(books: List<MBook>,
                        navController: NavController) {
    ListCard()



}

//test

