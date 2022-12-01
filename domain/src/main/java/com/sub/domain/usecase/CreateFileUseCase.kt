package com.sub.domain.usecase

import com.sub.domain.model.FileModel
import com.sub.domain.repository.FileRepository

class CreateFileUseCase(private val repository: FileRepository) {
    suspend fun execute(code: String, flowkey: String) : FileModel {
        val data = repository.createFile(code, flowkey)
        return FileModel.Builder()
            .setFileName(v = data.fileName)
            .setFilePath(v = data.filePath)
            .build()
    }
}