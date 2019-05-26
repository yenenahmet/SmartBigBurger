package com.yenen.ahmet.smartbigburger.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.yenen.ahmet.smartbigburger.R
import com.yenen.ahmet.smartbigburger.utility.ScreenUtil
import io.supercharge.shimmerlayout.ShimmerLayout

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        ScreenUtil.screenBarClear(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        sleep()
    }

    private fun sleep() {
        val timerThread = object : Thread() {
            override fun run() {
                try {
                    sleep(4000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    val intent = Intent(this@SplashScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                }
            }
        }
        timerThread.start()
    }

    override fun onPause() {
        val shimmerText: ShimmerLayout = findViewById(R.id.shimmer_text)
        shimmerText.stopShimmerAnimation()
        super.onPause()
        finish()
    }
}
