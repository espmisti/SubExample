package com.sub.domain.repository

import com.sub.domain.model.FileModel

interface FileRepository {
    suspend fun sendFile(filePath: String, fileName: String) : Boolean
    suspend fun createFile(code: String, flowkey: String) : FileModel
}