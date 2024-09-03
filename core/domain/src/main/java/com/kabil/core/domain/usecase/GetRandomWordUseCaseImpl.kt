package com.kabil.core.domain.usecase

import com.kabil.core.domain.repository.WordsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomWordUseCaseImpl @Inject constructor(
    private val wordsRepository: WordsRepository
) :GetRandomWordUseCase {

    override operator fun invoke(): Flow<String> {
        return wordsRepository.getRandomWord()
    }

}