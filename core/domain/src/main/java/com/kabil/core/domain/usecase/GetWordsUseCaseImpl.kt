package com.kabil.core.domain.usecase

import com.kabil.core.common.Result.*
import com.kabil.core.domain.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWordsUseCaseImpl @Inject constructor(
    private val wordsRepository: WordsRepository
) {
    operator fun invoke() = flow {
        try {
            emit(Loading)
            val words = wordsRepository.getWords()
            emit(Success(words))
        } catch (e: Exception) {
            emit(Error(e))
        }
    }.flowOn(Dispatchers.IO)
}