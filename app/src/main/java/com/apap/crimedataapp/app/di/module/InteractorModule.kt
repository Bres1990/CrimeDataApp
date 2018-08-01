package com.apap.crimedataapp.app.di.module

import com.apap.crimedataapp.map.interactor.GetCountryForLocationInteractor
import com.apap.crimedataapp.map.repository.LocationRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class InteractorModule {

    @Provides @Singleton fun provideGetCountryForLocationInteractor(locationRepository: LocationRepository) : GetCountryForLocationInteractor {
        return GetCountryForLocationInteractor(locationRepository)
    }
}