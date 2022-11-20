package com.sub.example.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sub.domain.usecase.*

class SplashViewModelFactory(
    val getFlowUseCase: GetFlowUseCase,
    val getCodeUseCase: GetCodeUseCase,
    val getInitUseCase: GetInitUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SplashViewModel(
            getFlowUseCase = getFlowUseCase,
            getCodeUseCase = getCodeUseCase,
            getInitUseCase = getInitUseCase
        ) as T
    }
}