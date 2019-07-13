package com.yenen.ahmet.smartbigburger.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory
import com.yenen.ahmet.smartbigburger.viewmodel.MainViewModel
import csetupNotificationChannelsom.yenen.ahmet.smartbigburger.di.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
internal abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel


    @Binds
    internal abstract fun bindViewModelFactory(appViewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}