package com.kabil.feature.welcome

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabil.core.domain.usecase.GetWordsUseCaseImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
internal class WelcomeViewModel @Inject constructor(
    getWordsUseCase: GetWordsUseCaseImpl
) : ViewModel() {

    val uiState: StateFlow<WelcomeUiState> =
        getWordsUseCase().map { result ->
            when (result) {
                is com.kabil.core.common.Result.Error -> {
                    WelcomeUiState.Error(result.exception)
                }

                com.kabil.core.common.Result.Loading -> {
                    WelcomeUiState.Loading
                }

                is com.kabil.core.common.Result.Success -> {
                    WelcomeUiState.Success
                }
            }

        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5.seconds.inWholeMilliseconds),
            initialValue = WelcomeUiState.Loading,
            )

}