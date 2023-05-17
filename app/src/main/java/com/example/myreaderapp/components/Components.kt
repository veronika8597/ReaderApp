package com.example.myreaderapp.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReaderLogo(modifier: Modifier = Modifier) {
    Text(text = "Reader",
        modifier = modifier.padding(bottom = 16.dp),
        style = MaterialTheme.typography.h3,
        color = Color.Red.copy(alpha = 0.5f)
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

