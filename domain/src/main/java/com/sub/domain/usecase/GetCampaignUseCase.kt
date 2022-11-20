package com.sub.domain.usecase

import com.sub.domain.repository.AppsflyerRepository

class GetCampaignUseCase(private val repository: AppsflyerRepository) {
    suspend fun execute() : String? {
        val data = repository.getData()
        return data?.campaign?.substringAfter("_")
    }
}