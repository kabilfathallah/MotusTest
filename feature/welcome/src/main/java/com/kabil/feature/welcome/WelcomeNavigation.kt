package com.kabil.feature.welcome

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object WelcomeNavigation

fun NavController.navigateToWelcome() = navigate(WelcomeNavigation)

fun NavGraphBuilder.welcomeScreen(
    onClick: () -> Unit,
    backOnHome: () -> Unit,
    listClick: () -> Unit
) {

    composable<WelcomeNavigation> {
        WelcomeRoute(
            startGameClick = onClick,
            backOnHome = backOnHome,
            listClick = listClick
        )
    }
}