package com.kabil.motustest

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.kabil.feature.game.gameScreen
import com.kabil.feature.game.navigateToGame
import com.kabil.feature.welcome.WelcomeNavigation
import com.kabil.feature.welcome.welcomeScreen

@Composable
fun MotusTestNavHost(
    modifier: Modifier = Modifier,
    backOnHome: () -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WelcomeNavigation
    ) {
        welcomeScreen(
            onClick = { navController.navigateToGame() },
            backOnHome = backOnHome
        )

        gameScreen {
            navController.navigateUp()
        }

    }

}