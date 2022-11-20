package com.sub.domain.repository

import com.sub.domain.model.AppsflyerModel

interface AppsflyerRepository {
    suspend fun getData() : AppsflyerModel?
    suspend fun isInit() : Boolean
}