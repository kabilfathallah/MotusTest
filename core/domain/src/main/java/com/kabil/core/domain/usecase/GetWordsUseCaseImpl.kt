package com.kabil.core.domain.usecase

import com.kabil.core.domain.repository.WordsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetWordsUseCaseImpl @Inject constructor(
    private val wordsRepository: WordsRepository
) : GetWordsUseCase {
    override operator fun invoke(): Flow<com.kabil.core.common.Result<List<String>>> = flow {
        emit(com.kabil.core.common.Result.Loading)
        wordsRepository.getWords().onSuccess {
            emit(com.kabil.core.common.Result.Success(it))
        }.onFailure {
            emit(com.kabil.core.common.Result.Error(it))
        }
    }.catch {
        emit(com.kabil.core.common.Result.Error(it))
    }.flowOn(Dispatchers.IO)
}