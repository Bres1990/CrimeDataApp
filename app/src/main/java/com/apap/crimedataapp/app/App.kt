package com.apap.crimedataapp.app

import android.app.Application
import com.apap.crimedataapp.app.di.component.CrimeDataComponent
import com.apap.crimedataapp.app.di.component.DaggerCrimeDataComponent
import com.apap.crimedataapp.app.di.module.AppModule

class App : Application() {

    private val component: CrimeDataComponent by lazy {
        DaggerCrimeDataComponent
                .builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        component.inject(this)
    }
}
