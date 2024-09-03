package com.kabil.core.domain.di

import com.kabil.core.domain.usecase.GetRandomWordUseCase
import com.kabil.core.domain.usecase.GetRandomWordUseCaseImpl
import com.kabil.core.domain.usecase.GetWordsUseCase
import com.kabil.core.domain.usecase.GetWordsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {


    //@Binds
    //fun bindsGetRandomWordUseCase(useCaseImpl: GetRandomWordUseCaseImpl): GetRandomWordUseCase


    //@Binds
    //fun bindsGetWordsUseCase(useCaseImpl: GetWordsUseCaseImpl): GetWordsUseCase

}