package com.apap.crimedataapp.app.di.module

import com.apap.crimedataapp.map.contract.LocationContract
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class LocationModule(private var view: LocationContract.View) {
    @Inject
    fun LocationModule(view: LocationContract.View) {
        this.view = view
    }

    @Provides
    fun provideView() : LocationContract.View {
        return view
    }
}
