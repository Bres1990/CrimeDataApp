package com.apap.crimedataapp.app.di.component

import com.apap.crimedataapp.app.di.module.LocationModule
import com.apap.crimedataapp.map.fragment.CrimeMapFragment
import dagger.Component

/**
 * Created by Adam on 2018-06-02.
 */

@Component(modules = arrayOf(LocationModule::class))
interface LocationComponent {
    fun inject(fragment: CrimeMapFragment)
}