package com.yenen.ahmet.smartbigburger.utility

import android.app.Activity
import android.view.View

object ScreenUtil {
    val list = listOf<Double>(7.0,-10.0,13.0,8.0,4.0,-7.2,-12.0,-3.7,3.5,-9.6,6.5,-1.7,-6.2,7.0)

    fun screenBarClear(activity: Activity) {
        val decorView = activity.window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }


    fun closestToZero(list: List<Double>): Double {
        if(!list.isEmpty()){
            val positive: Double? = list.filter { s -> s > 0 }.min()
            val negative: Double? = list.filter { s -> s < 0 }.max()
            if(positive!=null && negative!=null){
                if((negative*-1) >= positive){
                    return positive
                }
                return negative
            }
        }
        return 0.0
    }


}