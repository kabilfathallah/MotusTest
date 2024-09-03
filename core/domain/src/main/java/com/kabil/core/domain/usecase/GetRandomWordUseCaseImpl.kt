package com.kabil.core.domain.usecase

import com.kabil.core.domain.repository.WordsRepository
import javax.inject.Inject

class GetRandomWordUseCaseImpl @Inject constructor(
    private val wordsRepository: WordsRepository
) {

    suspend operator fun invoke(): String {
        return wordsRepository.getRandomWord()
    }

}