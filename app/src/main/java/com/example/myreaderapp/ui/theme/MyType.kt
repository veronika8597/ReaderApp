package com.example.myreaderapp.ui.theme
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.myreaderapp.R
import java.time.format.TextStyle

val BerkshireSwash = FontFamily(
    Font(R.font.berkshireswash_regular, FontWeight.W400)
)

@RequiresApi(Build.VERSION_CODES.O)
val BerkshireSwashTypography = Typography(
    h1 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W500,
        fontSize = 60.sp
    ),

    /*h2 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W500,
        fontSize = 24.sp
    ),

    h3 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W500,
        fontSize = 20.sp
    ),

    h4 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    ),

    h5 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),

    h6 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),

    subtitle1 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp
    ),

    subtitle2 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp
    ),

    body1 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),

    body2 = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontSize = 14.sp
    ),

    button = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 15.sp,
        color = Color.White
    ),

    caption = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp
    ),

    overline = androidx.compose.ui.text.TextStyle(
        fontFamily = BerkshireSwash,
        fontWeight = FontWeight.W400,
        fontSize = 13.sp
    )*/
)

