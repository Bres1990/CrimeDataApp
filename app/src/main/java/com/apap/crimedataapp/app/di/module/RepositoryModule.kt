package com.apap.crimedataapp.app.di.module

import com.apap.crimedataapp.map.repository.LocationRepository
import com.apap.crimedataapp.map.repository.LocationRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class RepositoryModule {

    @Provides fun provideLocationRepository(locationRepository: LocationRepositoryImpl) : LocationRepository {
        return locationRepository
    }
}