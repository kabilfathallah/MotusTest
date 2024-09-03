package com.kabil.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WordInput(
    word: String,
    onValueChange: ((String) -> Unit)? = null,
) {
    var text by remember { mutableStateOf("") }
    val totalBoxes = word.length
    BasicTextField(
        value = text,
        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Characters),
        onValueChange = {
            // prevent the user from deleting
            if (it.length > text.length && it.length <= totalBoxes - 1) {
                text = it.uppercase()
            }
            if (it.length == totalBoxes - 1) {
                text = ""
            }
            // notify only if a word is completed
            if (it.length + 1 == totalBoxes) if (onValueChange != null) {
                onValueChange(word.first() + it)
            }
        },
        textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
        decorationBox = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
            ) {

                // display the first letter of the mystery word in the first box
                if (totalBoxes > 0) {
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(50.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text = word.first().toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }

                repeat(totalBoxes - 1) { index ->
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .height(50.dp)
                            .fillMaxWidth()
                            .weight(1f)
                            .background(Color.LightGray, shape = RoundedCornerShape(4.dp)),
                        contentAlignment = Alignment.Center,
                    ) {
                        if (index < text.length) {
                            Text(
                                text = text[index].toString(),
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}