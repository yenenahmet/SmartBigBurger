package com.yenen.ahmet.smartbigburger.remote

import android.util.Log
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

class ApiClient{
    companion object {
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
