package com.kabil.core.domain.usecase

import com.kabil.core.common.Result
import kotlinx.coroutines.flow.Flow


interface GetWordsUseCase {
    operator fun invoke(): Flow<Result<List<String>>>
}