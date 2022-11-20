package com.sub.domain.repository

import com.sub.domain.model.CodeModel

interface CodeRepository {
    suspend fun getData(flowkey: String) : CodeModel?
}