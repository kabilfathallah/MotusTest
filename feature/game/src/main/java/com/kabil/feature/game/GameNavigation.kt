package com.kabil.feature.game

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object GameNavigation

fun NavController.navigateToGame() = navigate(GameNavigation)

fun NavGraphBuilder.gameScreen(
    navigateBack: () -> Unit
) {

    composable<GameNavigation> {
        GameRoute {
            navigateBack.invoke()
        }

    }
}

