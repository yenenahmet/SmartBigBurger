package com.yenen.ahmet.smartbigburger.di.component

import android.app.Application
import com.yenen.ahmet.smartbigburger.di.module.ActivityModule
import com.yenen.ahmet.smartbigburger.di.module.AppModule
import com.yenen.ahmet.smartbigburger.di.module.NetworkModule
import com.yenen.ahmet.smartbigburger.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Singleton

@Suppress("unused")
@Singleton
@Component(modules = [
    (AndroidInjectionModule::class),
    (ActivityModule::class),
    (ViewModelModule::class),
    (AppModule::class),
    (NetworkModule::class)])
interface AppComponent : AndroidInjector<DaggerApplication> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: DaggerApplication)
}