package com.sub.domain.usecase

import com.sub.domain.repository.CodeRepository

class GetCodeUseCase(private val repository: CodeRepository) {
    suspend fun execute(flowkey: String) = repository.getData(flowkey = flowkey)
}