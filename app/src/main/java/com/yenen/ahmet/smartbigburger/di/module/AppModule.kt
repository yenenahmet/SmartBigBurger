package com.yenen.ahmet.smartbigburger.di.module

import dagger.Module
import android.app.Application
import android.content.Context
import javax.inject.Singleton
import dagger.Provides

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }
}