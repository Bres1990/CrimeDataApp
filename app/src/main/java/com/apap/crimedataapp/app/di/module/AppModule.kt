package com.apap.crimedataapp.app.di.module

import com.apap.crimedataapp.app.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module class AppModule(val app: App) {
    @Provides @Singleton fun provideApp() = app
}