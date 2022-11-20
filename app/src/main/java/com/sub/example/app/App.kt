package com.sub.example.app

import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.appsflyer.attribution.AppsFlyerRequestListener
import com.sub.common.Constants
import com.sub.example.di.AppComponent
import com.sub.example.di.AppModule
import com.sub.example.di.DaggerAppComponent
import kotlin.coroutines.resume

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
            .builder()
            .appModule(AppModule(context = this))
            .build()
        AppsFlyerLib.getInstance().init(Constants.AF_KEY, null, this)
        AppsFlyerLib.getInstance().setDebugLog(true)
    }
}