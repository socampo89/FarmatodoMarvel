package com.farmatodo.di.modules

import com.farmatodo.application.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: App) {
    @Provides
    @Singleton
    fun provideContext(): App = application
}