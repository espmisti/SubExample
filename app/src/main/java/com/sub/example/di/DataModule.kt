package com.sub.example.di

import android.content.Context
import com.sub.data.repository.AppsflyerRepositoryImpl
import com.sub.data.repository.CodeRepositoryImpl
import dagger.Module
import dagger.Provides

@Module
class DataModule {
    @Provides
    fun provideAppsflyerRepository(context: Context) = AppsflyerRepositoryImpl(context)

    @Provides
    fun provideCodeRepository() = CodeRepositoryImpl()
}