package com.sub.data

import com.sub.common.Constants
import com.sub.domain.model.CodeModel
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface APIService {

    @GET(Constants.API.ROUTES.GET_CODE)
    suspend fun getCode(
        @Header("Key") token : String = Constants.API.TOKEN,
        @Query("flowkey") flowkey: String
    ) : Response<CodeModel>

    companion object {
        val okHttpClient by lazy {
            OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        }

        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(Constants.API.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }

}