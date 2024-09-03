package com.kabil.core.ui

import androidx.compose.runtime.Composable

@Composable
fun LogoPlaceHolder() {
    WordOutput(
        lettersResult = listOf(
            LetterFeedback('M', LetterStatus.CORRECT),
            LetterFeedback('O', LetterStatus.PRESENT),
            LetterFeedback('T', LetterStatus.CORRECT),
            LetterFeedback('U', LetterStatus.CORRECT),
            LetterFeedback('S', LetterStatus.CORRECT),
        )
    )
}