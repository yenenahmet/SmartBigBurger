package com.yenen.ahmet.smartbigburger.remote

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class ApiClient{
    companion object {
        @Volatile
        private var retrofit: Retrofit? = null

        fun <S> createService(sClass: Class<S>,context: Context): S {
            retrofit ?: synchronized(this) {
                retrofit ?: Retrofit.Builder()
                    .baseUrl("http://legacy.vibuy.com/dump/")
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .client(getClient(context))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create()).build().also { retrofit = it }
            }
            return retrofit!!.create(sClass)
        }

        private fun getGson(): Gson {
            return GsonBuilder()
                .setLenient()
                .create()
        }

        private fun getClient(context :Context) :OkHttpClient{
            val cacheSize:Long = 10*1024*1024 // 10MB
            val cache =Cache(context.cacheDir,cacheSize)
            return  OkHttpClient.Builder().cache(cache).build()
        }
        // Error Handler Message // gelen hata mesajına göre handler edilip geriye ona göre mesaj yollanabilir !
        fun failMessage(t: Throwable): String {
            Log.e("ApiClientFailMessage", t.toString())
            return if (t is UnknownHostException || t is IOException) {
                "There is a problem with your Internet connection! Connection could not be established"
            } else if (t is JsonSyntaxException) {
                "A Structural Problem Has Occurred"
            } else if (t is TimeoutException) {
                "A Structural Problem OccuredLink Timeout"
            }else if(t is HttpException){
                val response = t.response()
                Log.e("HttpExCode =",response.code().toString())
                "Connection problem!"
            } else{
                "Error retrieving data ! "
            }
        }

    }
}
