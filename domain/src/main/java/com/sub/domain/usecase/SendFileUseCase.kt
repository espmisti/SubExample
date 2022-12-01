package com.sub.domain.usecase

import com.sub.domain.repository.FileRepository

class SendFileUseCase(private val repository: FileRepository) {
    suspend fun execute(path: String, fileName: String) = repository.sendFile(path, fileName)
}