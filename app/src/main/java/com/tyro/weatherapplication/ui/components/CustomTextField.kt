package com.tyro.weatherapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextField(
    location: String,
    onValueChange: (String)-> Unit,
    keyboard: KeyboardOptions,
    placeholder: String,
){
    Box(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 40.dp).height(44.dp)
            .clip(RoundedCornerShape(30))
            .background(color = MaterialTheme.colorScheme.surfaceDim.copy(0.8f))
            .border(width = 1.dp, shape = RoundedCornerShape(30), color = MaterialTheme.colorScheme.onBackground.copy(0.2f))
    ){
        BasicTextField(
            modifier = Modifier.align(Alignment.Center).fillMaxWidth()
                .padding(horizontal = 16.dp),
            value = location,
            keyboardOptions = keyboard,
            textStyle = TextStyle.Default.copy(color = MaterialTheme.colorScheme.onSurface),
            onValueChange = {onValueChange(it)},
            singleLine = true,
            //Adding the custom placeholder
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    // Placeholder
                    if (location.isEmpty()) {
                        Text(
                            text = placeholder,
                            style = TextStyle(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                            )
                        )
                    }
                    // Actual text field
                    innerTextField()
                }
            }
        )
    }

}