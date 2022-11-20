package com.sub.domain.usecase

import com.sub.domain.repository.AppsflyerRepository

class GetInitUseCase(private val repository: AppsflyerRepository) {
    suspend fun execute() = repository.isInit()
}