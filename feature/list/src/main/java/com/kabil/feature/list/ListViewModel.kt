package com.kabil.feature.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kabil.core.domain.usecase.GetWordsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class ListViewModel @Inject constructor(
    getWordsUseCase: GetWordsUseCase
) : ViewModel() {

    val uiState = getWordsUseCase().map { result ->
        when (result) {
            com.kabil.core.common.Result.Loading -> {
                ListUiState.Loading
            }

            is com.kabil.core.common.Result.Success -> {
                ListUiState.Success(result.data)
            }

            else -> {
                ListUiState.Loading
            }
        }

    }.stateIn(
        scope = viewModelScope,
        started = kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed(5.seconds.inWholeMilliseconds),
        initialValue = ListUiState.Loading
    )

}