package com.kabil.feature.list

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInParent
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.launch
import kotlin.math.abs


@Composable
fun ListRoute(
    modifier: Modifier = Modifier,
    viewModel: ListViewModel = hiltViewModel(),
    navigateBack: (() -> Unit)?
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ListScreen(
        modifier = modifier,
        uiState = uiState,
        navigateBack = navigateBack
    )
}

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    uiState: ListUiState,
    navigateBack: (() -> Unit)?
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        IconButton(onClick = {
            navigateBack?.invoke()
        }) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "")
        }

        when (uiState) {
            is ListUiState.Loading -> {

                CircularProgressIndicator()

            }

            is ListUiState.Success -> {

                Row {
                    val listState = rememberLazyListState()
                    val headers = remember {
                        uiState.words.map { it.first().uppercase() }.toSet().toList()
                    }
                    LazyColumn(
                        modifier = Modifier.weight(1f),
                        state = listState
                    ) {
                        items(uiState.words.size) { index ->
                            Text(
                                modifier = Modifier.fillMaxWidth(),
                                text = uiState.words[index]
                            )
                        }

                    }

                    val offsets = remember { mutableStateMapOf<Int, Float>() }
                    var selectedHeaderIndex by remember { mutableIntStateOf(0) }
                    val scope = rememberCoroutineScope()

                    fun updateSelectedIndexIfNeeded(offset: Float) {
                        val index = offsets
                            .mapValues { abs(it.value - offset) }
                            .entries
                            .minByOrNull { it.value }
                            ?.key ?: return
                        if (selectedHeaderIndex == index) return
                        selectedHeaderIndex = index
                        val selectedItemIndex = uiState.words.indexOfFirst {
                            it.first().uppercase() == headers[selectedHeaderIndex]
                        }
                        scope.launch {
                            listState.scrollToItem(selectedItemIndex)
                        }
                    }

                    Column(
                        verticalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier
                            .fillMaxHeight()
                            .pointerInput(Unit) {
                                detectTapGestures {
                                    updateSelectedIndexIfNeeded(it.y)
                                }
                            }
                            .pointerInput(Unit) {
                                detectVerticalDragGestures { change, _ ->
                                    updateSelectedIndexIfNeeded(change.position.y)
                                }
                            }
                    ) {
                        headers.forEachIndexed { index, _ ->
                            Text(text = headers[index],
                                modifier = Modifier.onGloballyPositioned {
                                    offsets[index] = it.boundsInParent().center.y
                                })
                        }
                    }

                }

            }
        }


    }

}