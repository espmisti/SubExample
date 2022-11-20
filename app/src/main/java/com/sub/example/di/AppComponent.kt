package com.sub.example.di

import com.sub.example.presentation.splash.SplashFragment
import dagger.Component

@Component(modules = [AppModule::class, DomainModule::class, DataModule::class])
interface AppComponent {
    fun inject(fragment: SplashFragment)
}