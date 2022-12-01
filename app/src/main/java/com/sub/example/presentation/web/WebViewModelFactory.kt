package com.sub.example.presentation.web

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sub.domain.usecase.CreateFileUseCase
import com.sub.domain.usecase.SendFileUseCase

class WebViewModelFactory(
    val createFileUseCase: CreateFileUseCase,
    val sendFileUseCase: SendFileUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WebViewModel(
            createFileUseCase = createFileUseCase,
            sendFileUseCase = sendFileUseCase
        ) as T
    }
}