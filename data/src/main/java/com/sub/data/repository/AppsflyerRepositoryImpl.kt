package com.sub.data.repository

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.sub.common.Constants
import com.sub.domain.model.AppsflyerModel
import com.sub.domain.repository.AppsflyerRepository
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AppsflyerRepositoryImpl(private val context: Context) : AppsflyerRepository {
    private val TAG = "APP_CHECK_APPSFLYER"
    override suspend fun getData(): AppsflyerModel? = suspendCoroutine {
        val conversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(map: MutableMap<String, Any>?) {
                it.resume(AppsflyerModel.Builder(campaign = map?.get("campaign").toString()).build())
            }

            override fun onConversionDataFail(p0: String?) {
                Log.e(TAG, "[Appsflyer]: ConversionDataFail ($p0)")
            }

            override fun onAppOpenAttribution(p0: MutableMap<String, String>?) {
                Log.i("APP_CHECK", "onAppOpenAttribution: ")
            }

            override fun onAttributionFailure(p0: String?) {
                Log.e(TAG, "[Appsflyer]: AttributionFailure ($p0)")
            }
        }
        AppsFlyerLib.getInstance().registerConversionListener(context, conversionListener)
    }

    override suspend fun isInit(): Boolean = suspendCoroutine {
        AppsFlyerLib.getInstance().start(context, Constants.AF_KEY, object : AppsFlyerRequestListener {
            override fun onSuccess() {
                it.resume(value = true)
            }
            override fun onError(p0: Int, p1: String) {
                Log.e(TAG, "[Appsflyer]: Init error ($p0 | $p1)")
                it.resume(value = false)
            }
        })
    }
}