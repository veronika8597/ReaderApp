package com.example.myreaderapp.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.myreaderapp.model.MBook
import com.example.myreaderapp.navigation.ReaderScreens
import com.example.myreaderapp.ui.theme.BerkshireSwash
import com.example.myreaderapp.ui.theme.BerkshireSwashTypography
import com.example.myreaderapp.ui.theme.Green500
import com.example.myreaderapp.ui.theme.Green700
import com.google.firebase.auth.FirebaseAuth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(text = "BloomReads",
        modifier = modifier.padding(top = 35.dp ,bottom = 80.dp),
        style = BerkshireSwashTypography.h1,
        color = Color(0xFF62846E)
    )
}


@Composable
fun EmailInput(modifier: Modifier = Modifier,
               emailState: MutableState<String>,
               lableId: String = "Email",
               enabled: Boolean,
               isSingleLine: Boolean = true,
               imeAction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default
){

    InputField(modifier = modifier,
        valueState = emailState,
        lableId = lableId,
        enabled = enabled,
        keyboardType = KeyboardType.Email,
        imeAction = imeAction,
        onAction = onAction
    )
}

@Composable
fun InputField(modifier: Modifier = Modifier,
               valueState: MutableState<String>,
               lableId: String,
               enabled: Boolean,
               isSingleLine: Boolean = true,
               keyboardType: KeyboardType = KeyboardType.Text,
               imeAction: ImeAction = ImeAction.Next,
               onAction: KeyboardActions = KeyboardActions.Default) {

    OutlinedTextField(value = valueState.value, onValueChange = {valueState.value = it},
        label = { Text(text = lableId)},
        singleLine = isSingleLine,
        textStyle = TextStyle(fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = imeAction),
        keyboardActions = onAction)

}



@Composable
fun PasswordInput(modifier: Modifier,
                  passwordState: MutableState<String>,
                  lableId: String,
                  enabled: Boolean,
                  passwordVisibility: MutableState<Boolean>,
                  imeAction: ImeAction = ImeAction.Done,
                  onAction: KeyboardActions = KeyboardActions.Default) {

    val visualTransformation = if (passwordVisibility.value) VisualTransformation.None else
        PasswordVisualTransformation()

    OutlinedTextField(value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = lableId) },
        singleLine = true,
        textStyle = TextStyle(
            fontSize = 18.sp,
            color = MaterialTheme.colors.onBackground
        ),
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = imeAction
        ),
        visualTransformation = visualTransformation,
        trailingIcon = { PasswordVisibility(passwordVisibility = passwordVisibility)},
        keyboardActions = onAction)
}

@Composable
fun BookRating(score: Double = 4.5) {
    Surface(modifier = Modifier
        .height(70.dp)
        .padding(4.dp), shape = RoundedCornerShape(56.dp), elevation = 6.dp, color = Color.White) {
        Column(modifier = Modifier.padding(4.dp)) {
            Icon(imageVector = Icons.Filled.StarBorder, contentDescription = "Star", modifier = Modifier.padding(3.dp))
            Text(text = score.toString(), style = MaterialTheme.typography.subtitle1)

        }

    }

}

@Preview
@Composable
fun ListCard(book : MBook = MBook("sdsdsd","Running","Me and you","hello world"), onPressDetails: (String) -> Unit = {}){
    val context = LocalContext.current
    val resources = context.resources

    val displayMetrics = resources.displayMetrics

    val screenWeight = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 10.dp

    Card(shape = RoundedCornerShape(29.dp), backgroundColor = Color.White, elevation = 6.dp, modifier = Modifier
        .padding(16.dp)
        .height(242.dp)
        .width(202.dp)
        .clickable {
            onPressDetails.invoke(
                book.title.toString()
            )
        }) {

        Column(modifier = Modifier.width(screenWeight.dp - (spacing * 2)), horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.Center) {

                Image(painter = rememberImagePainter(data = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSbTX0mvWV40iwuM5P_deNJfg16J-MmUTMQCyVl2KEIkQ&s"), contentDescription = "book Image", modifier = Modifier
                    .height(140.dp)
                    .width(100.dp)
                    .padding(4.dp))
                Spacer(modifier = Modifier.width(50.dp))

                Column(modifier = Modifier.padding(top = 25.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(imageVector = Icons.Rounded.FavoriteBorder, contentDescription = "Fav Icon", modifier = Modifier.padding(bottom = 1.dp))

                    BookRating(score = 3.5)




                }

            }
            Text(text = book.title.toString(), modifier = Modifier.padding(4.dp), fontWeight = FontWeight.Bold, maxLines = 2, overflow = TextOverflow.Ellipsis)
            Text(text = book.authors.toString(), modifier = Modifier.padding(4.dp), style = MaterialTheme.typography.caption)



        }

        Row(horizontalArrangement = Arrangement.End, verticalAlignment = Alignment.Bottom) {
            RoundedButton(label = "Reading", radius = 70)


        }


    }


}
@Preview
@Composable
fun RoundedButton(label: String = "Reading", radius: Int = 29,onPress: () -> Unit = {}){

    Surface(modifier = Modifier.clip(RoundedCornerShape(bottomEndPercent = radius, topStartPercent = radius)), color = Color(0xFF9DC087)) {
        Column(modifier = Modifier
            .width(90.dp)
            .heightIn(40.dp)
            .clickable { onPress.invoke() }, verticalArrangement =  Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

            Text(text = label, style = TextStyle(color = Color.White, fontSize = 15.sp) )

        }
    }
}

@Composable
fun PasswordVisibility(passwordVisibility: MutableState<Boolean>) {

    val visible = passwordVisibility.value
    IconButton(onClick = { passwordVisibility.value = !visible}) {
        Icons.Default.Close

    }

}


@Composable
fun TitleSection(modifier: Modifier = Modifier, label:String){
    Surface(modifier = modifier.padding(start = 5.dp, top = 1.dp)) {
        Column {
            Text(text = label,
                fontSize = 19.sp,
                fontStyle = FontStyle.Normal,
                textAlign = TextAlign.Left)
        }
    }
}

@Composable
fun ReaderAppBar(
    title: String,
    icon: ImageVector? = null,
    showProfile: Boolean = true,
    navController: NavController,
    onBackArrowClicked: () -> Unit = {}
){

    TopAppBar(title = {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showProfile){
                Image(imageVector = Icons.Default.Favorite,
                    contentDescription = "Logo Icon",
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .scale(0.8f))
            }
            if(icon != null){
                Icon(imageVector = icon, contentDescription = "arrow back",
                tint = Green700,
                modifier = Modifier.clickable { onBackArrowClicked.invoke() })
            }
            Spacer(modifier = Modifier.width(40.dp))
            Text(text = title,
                color = Color(0XFFF88379),
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp, fontFamily = BerkshireSwash))



        }

    },
        actions = {
            IconButton(onClick = {
                FirebaseAuth.getInstance().signOut().run {
                    navController.navigate(ReaderScreens.LoginScreen.name)
                }
            }) {
                if(showProfile) Row() {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout",
                        tint = Green700.copy(alpha = 0.9f)
                    )
                }else Box(modifier = Modifier)
                    

            }
        },
        backgroundColor = Color.Transparent,
        elevation = 0.dp)



}



@Composable
fun FABContent(onTap: () -> Unit) {
    FloatingActionButton(onClick = {onTap() },
        shape = RoundedCornerShape(50.dp),
        backgroundColor = Green500
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add a Book",
            tint = Color.White)
    }
}


