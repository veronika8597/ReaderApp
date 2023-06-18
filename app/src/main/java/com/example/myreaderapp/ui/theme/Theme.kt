package com.example.myreaderapp.ui.theme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFF743E39),
    primaryVariant = Color(0xFFA0C2AC),
    secondary = Color(0xFF233516)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFF88379),
    primaryVariant = Color(0xFF466B53),
    secondary = Color(0xFFCDE6BA)
)

//0xFF2F4E3A



@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyReaderAppTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = QuickSandTypography,
        shapes = Shapes,
        content = content
    )

    MaterialTheme(
        colors = colors,
        typography = BerkshireSwashTypography,
        shapes = Shapes,
        content = content
    )
}