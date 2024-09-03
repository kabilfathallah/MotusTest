package com.kabil.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface GetRandomWordUseCase {
   operator fun invoke(): Flow<String>
}