package com.kabil.feature.welcome

import androidx.activity.ComponentActivity
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.kabil.welcome.R
import org.junit.Rule
import org.junit.Test
import java.io.IOException

class WelcomeScreenUiTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun testIsLoading() {
        composeTestRule.setContent {
            WelcomeScreen(uiState = WelcomeUiState.Loading,
                startGameClick = { }) {

            }
        }

        composeTestRule
            .onNode(SemanticsMatcher.keyIsDefined(SemanticsProperties.ProgressBarRangeInfo))
            .assertIsDisplayed()
    }

    @Test
    fun testIsFailWithIOException() {
        composeTestRule.setContent {
            WelcomeScreen(uiState = WelcomeUiState.Error(IOException()),
                startGameClick = { }) {

            }
        }

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.no_internet_error))
            .assertIsDisplayed()
    }

    @Test
    fun testGenericError() {
        composeTestRule.setContent {
            WelcomeScreen(uiState = WelcomeUiState.Error(Exception()),
                startGameClick = { }) {
            }
        }

        composeTestRule
            .onNodeWithText(composeTestRule.activity.getString(R.string.generic_error))
            .assertIsDisplayed()

    }

    @Test
    fun testSuccess() {

        composeTestRule.setContent {
            WelcomeScreen(uiState = WelcomeUiState.Success,
                startGameClick = { }) {
            }
        }

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.welcome_start)).assertIsDisplayed()
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.welcome_words)).assertIsDisplayed()

    }

}