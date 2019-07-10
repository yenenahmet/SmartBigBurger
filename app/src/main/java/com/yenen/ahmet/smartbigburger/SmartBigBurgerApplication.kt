package com.yenen.ahmet.smartbigburger

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.yenen.ahmet.smartbigburger.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import java.io.IOException

class SmartBigBurgerApplication : DaggerApplication() {

    private val appComponent = DaggerAppComponent.builder()
        .application(this)
        .build()

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        initView()

    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    private fun initView() {
        Thread(Runnable {
            try {
                Log.i("FCM", FirebaseInstanceId.getInstance().getToken(getString(R.string.SENDER_ID),"FCM"))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }).start()
    }
}