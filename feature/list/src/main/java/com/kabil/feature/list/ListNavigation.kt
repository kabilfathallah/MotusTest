package com.kabil.feature.list

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object ListNavigation

fun NavController.navigateToList() = navigate(ListNavigation)

fun NavGraphBuilder.listScreen(
    navigateBack: () -> Unit
) {

    composable<ListNavigation> {
        ListRoute {
            navigateBack.invoke()
        }

    }
}
