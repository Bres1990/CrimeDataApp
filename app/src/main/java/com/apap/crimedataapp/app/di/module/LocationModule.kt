package com.apap.crimedataapp.app.di.module

import com.apap.crimedataapp.map.contract.LocationContract
import com.apap.crimedataapp.map.presenter.LocationPresenter
import com.apap.crimedataapp.map.repository.LocationRepository
import com.apap.crimedataapp.map.source.RemoteCountrySource
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module(includes = arrayOf(RepositoryModule::class))
class LocationModule @Inject constructor(var view: LocationContract.View) {

    @Provides
    fun provideView(): LocationContract.View {
        return view
    }
}
