package com.farmatodo.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.farmatodo.di.components.AppComponent
import com.farmatodo.di.components.DaggerAppComponent
import com.farmatodo.di.modules.AppModule

class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var appComponent: AppComponent
    }

    private fun initializeDagger() {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
        instance = this
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }
}