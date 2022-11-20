package com.sub.example.di

import android.content.Context
import com.sub.domain.usecase.*
import com.sub.example.presentation.splash.SplashViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides
    fun provideContext() =
        context

    @Provides
    fun provideSplashViewModelFactory(
        getFlowUseCase: GetFlowUseCase,
        getCodeUseCase: GetCodeUseCase,
        getInitUseCase: GetInitUseCase
    ) =
        SplashViewModelFactory(
            getFlowUseCase = getFlowUseCase,
            getCodeUseCase = getCodeUseCase,
            getInitUseCase = getInitUseCase
        )
}