package com.yenen.ahmet.smartbigburger.di.module

import com.yenen.ahmet.smartbigburger.view.MainActivity
import com.yenen.ahmet.smartbigburger.view.ProductSummaryActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSearchActivity(): ProductSummaryActivity

}