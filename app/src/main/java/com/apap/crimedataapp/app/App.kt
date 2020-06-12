package com.apap.crimedataapp.app

import android.app.Application
import com.apap.crimedataapp.app.di.component.CrimeDataComponent
import com.apap.crimedataapp.app.di.component.DaggerCrimeDataComponent
import com.apap.crimedataapp.app.di.module.AppModule
import com.mapbox.mapboxsdk.Mapbox

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
        Mapbox.getInstance(applicationContext, "pk.eyJ1IjoiYnJlczE5OTAiLCJhIjoiY2s3OTJwZHduMGw3ZDNsbnl5d3gxZ3FiMiJ9.2Q9x3rLq6CR2bqYRYMwlWg")
    }
}
