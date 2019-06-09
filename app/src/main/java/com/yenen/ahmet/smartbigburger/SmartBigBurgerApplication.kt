package com.yenen.ahmet.smartbigburger

import com.yenen.ahmet.smartbigburger.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SmartBigBurgerApplication : DaggerApplication() {

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}