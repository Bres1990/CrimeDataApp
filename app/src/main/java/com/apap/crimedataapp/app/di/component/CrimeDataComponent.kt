package com.apap.crimedataapp.app.di.component

import com.apap.crimedataapp.app.App
import com.apap.crimedataapp.app.di.module.*
import com.apap.crimedataapp.map.CrimeMapFragment
import dagger.Component

@Component(modules = arrayOf(AppModule::class, ContextModule::class, RepositoryModule::class, LocationModule::class, NetModule::class, InteractorModule::class))
interface CrimeDataComponent {
    fun inject(app: App)
    fun inject(fragment: CrimeMapFragment)
    fun plus(locationModule: LocationModule)
}
