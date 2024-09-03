package com.kabil.feature.welcome

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kabil.core.ui.LogoPlaceHolder
import com.kabil.welcome.R
import java.io.IOException

@Composable
internal fun WelcomeRoute(
    modifier: Modifier = Modifier,
    viewModel: WelcomeViewModel = hiltViewModel(),
    startGameClick: () -> Unit,
    backOnHome: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WelcomeScreen(
        modifier = modifier,
        uiState = uiState,
        startGameClick = startGameClick
    )
    BackHandler {
        backOnHome.invoke()
    }
}

@Composable
fun WelcomeScreen(
    modifier: Modifier = Modifier,
    uiState: WelcomeUiState,
    startGameClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        LogoPlaceHolder()
        Spacer(modifier = Modifier.weight(1f))
        when (uiState) {

            is WelcomeUiState.Loading -> {
                CircularProgressIndicator()
            }

            is WelcomeUiState.Success -> {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    onClick = { startGameClick.invoke() }) {
                    Text(text = stringResource(id = R.string.welcome_start))

                }
            }

            is WelcomeUiState.Error -> {
                val errorText = when (uiState.it) {
                    is IOException -> stringResource(id = R.string.no_internet_error)
                    else -> stringResource(id = R.string.generic_error)
                }
                Text(text = errorText)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }

}


@Preview
@Composable
private fun WelcomeScreenLoadingPreview() {
    WelcomeScreen(
        uiState = WelcomeUiState.Loading
    ) {

    }
}

@Preview
@Composable
private fun WelcomeScreenErrorPreview() {
    WelcomeScreen(
        uiState = WelcomeUiState.Error(Throwable("Error"))
    ) {

    }
}

@Preview
@Composable
private fun WelcomeScreenSuccessPreview() {
    WelcomeScreen(
        uiState = WelcomeUiState.Success
    ) {

    }
}
