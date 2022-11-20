package com.sub.data.repository

import com.sub.data.APIService
import com.sub.domain.model.CodeModel
import com.sub.domain.repository.CodeRepository

class CodeRepositoryImpl : CodeRepository {
    override suspend fun getData(flowkey: String): CodeModel? {
        val response = APIService.retrofit.getCode(flowkey = flowkey)
        return if(response.isSuccessful) {
            val data = response.body()
            data?.let {
                return CodeModel.Builder(code = it.code, url = it.url).build()
            } ?: run {
                return null
            }
        } else null
    }
}