package com.example.myreaderapp.screens.details

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.util.Log
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.components.ReaderAppBar
import com.example.myreaderapp.components.RoundedButton
import com.example.myreaderapp.data.Resource
import com.example.myreaderapp.model.Item
import com.example.myreaderapp.model.MBook
import com.example.myreaderapp.navigation.ReaderScreens
import com.example.myreaderapp.ui.theme.Coral500
import com.example.myreaderapp.ui.theme.QuickSandTypography
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

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
                        Text("Loading...", style = QuickSandTypography.h4)
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
            painter = rememberImagePainter(data = bookData?.imageLinks?.thumbnail),
            contentDescription = "Book Image",
            modifier = Modifier
                .width(90.dp)
                .height(90.dp)
                .padding(1.dp)
        )
    }

    Text(
        text = bookData?.title.toString(),
        style = QuickSandTypography.h2,
        overflow = TextOverflow.Ellipsis,
        maxLines = 19
    )

    Text(text = "Authors: ${bookData?.authors.toString()}", style = QuickSandTypography.h4)
    Text(text = "Page Count: ${bookData?.pageCount.toString()}", style = QuickSandTypography.h4)
    Text(
        text = "Categories: ${bookData?.categories.toString()}",
        style = QuickSandTypography.subtitle1,
        maxLines = 3,
        overflow = TextOverflow.Ellipsis
    )
    Text(
        text = "Published: ${bookData?.publishedDate.toString()}",
        style = QuickSandTypography.subtitle1
    )

    Spacer(modifier = Modifier.height(5.dp))

    val cleanDescription = bookData?.description?.let {
        HtmlCompat.fromHtml(
            it,
            HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    }

    val localDims = LocalContext.current.resources.displayMetrics

    Surface(modifier = Modifier
        .height(localDims.heightPixels.dp.times(0.09f))
        .padding(4.dp),
        shape = RectangleShape,
        border = BorderStroke(1.dp, Coral500)
    ) {

            LazyColumn(modifier = Modifier.padding(3.dp)) {
                item {
                    if (cleanDescription != null) {
                        Text(text = cleanDescription, style = QuickSandTypography.h5)
                    }
                }
            }
    }

    //Buttons:
    Row(modifier = Modifier.padding(top = 6.dp),
    horizontalArrangement = Arrangement.SpaceAround) {
        RoundedButton(label = "Save"){
            //Save the book into the FireBase DB
            val book = MBook(
                title = bookData?.title,
                authors = bookData?.authors.toString(),
                description = bookData?.description,
                categories = bookData?.description,
                notes = "",
                photoUrl = bookData?.imageLinks?.thumbnail,
                publishedDate = bookData?.publishedDate,
                pageCount = bookData?.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            saveToFirebase(book, navController = navController)
        }
        Spacer(modifier = Modifier.width(25.dp))
        RoundedButton(label = "Cancel"){
            navController.popBackStack()
        }

    }
}


fun saveToFirebase(book: MBook, navController: NavController) {

    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()){

        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener{task ->
                        if (task.isSuccessful){
                            navController.popBackStack()
                        }
                    }.addOnFailureListener {
                        Log.w(TAG, "SaveToFirebase: Error updating doc", it)
                    }
            }

    }else{

    }


}
