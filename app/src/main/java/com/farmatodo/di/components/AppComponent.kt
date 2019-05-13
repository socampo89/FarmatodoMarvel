package com.farmatodo.di.components

import com.farmatodo.application.App
import com.farmatodo.di.modules.ApiModule
import com.farmatodo.di.modules.AppModule
import com.farmatodo.interfaces.ApiServices
import dagger.Component
import javax.inject.Singleton

@Component(modules = [(AppModule::class), (ApiModule::class)])
@Singleton
interface AppComponent {
    fun appContext() : App
    fun apiService(): ApiServices
}