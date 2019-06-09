package com.yenen.ahmet.smartbigburger.di.module

import android.content.Context
import com.yenen.ahmet.smartbigburger.remote.BurgerService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.google.gson.GsonBuilder
import com.google.gson.Gson

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(context : Context): OkHttpClient {
        val cacheSize:Long = 10*1024*1024 // 10MB
        val cache = Cache(context.cacheDir,cacheSize)
        return OkHttpClient.Builder().cache(cache).build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient,gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://legacy.vibuy.com/dump/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun provideBurgerService(retrofit: Retrofit): BurgerService {
        return retrofit.create(BurgerService::class.java)
    }



}