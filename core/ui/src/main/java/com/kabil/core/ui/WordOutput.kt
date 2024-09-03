package com.kabil.core.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WordOutput(lettersResult: List<LetterFeedback>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        lettersResult.forEach { lettersResult ->
            val color = when (lettersResult.status) {
                LetterStatus.CORRECT -> Color.Red
                LetterStatus.PRESENT -> Color.Yellow
                LetterStatus.ABSENT -> Color.Gray
            }

            Box(
                modifier = Modifier
                    .padding(4.dp)
                    .height(50.dp)
                    .fillMaxWidth()
                    .weight(1f)
                    .background(color, shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = lettersResult.letter.toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        }
    }
}

enum class LetterStatus {
    CORRECT,      // letter is correct and in the correct position
    PRESENT,      // letter is correct but in the wrong position
    ABSENT        // letter is not in the word at all
}

data class LetterFeedback(
    val letter: Char,
    val status: LetterStatus
)