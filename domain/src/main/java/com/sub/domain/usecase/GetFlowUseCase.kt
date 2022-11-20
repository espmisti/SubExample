package com.sub.domain.usecase

import com.sub.domain.repository.AppsflyerRepository

class GetFlowUseCase(private val repository: AppsflyerRepository) {
    suspend fun execute() : String? {
        val flowkey = repository.getData()?.campaign
        flowkey?.let {
            return it.substringBefore("_")
        } ?: run {
            return null
        }
    }
}