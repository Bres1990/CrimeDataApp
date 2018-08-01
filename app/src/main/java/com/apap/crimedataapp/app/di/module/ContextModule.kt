package com.apap.crimedataapp.app.di.module

import android.content.Context
import dagger.Module
import dagger.Provides

@Module class ContextModule {

    private lateinit var context: Context

    fun ContextModule(context: Context) {
        this.context = context
    }

    @Provides fun provideContext() : Context {
        return context
    }
}