package com.yenen.ahmet.smartbigburger.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ProductModel(
    val ref: String, val title: String,
    val description: String, val thumbnail: String,
    val price: Double,
    var quantity:Int,
    var totalPrice :Double
) : Parcelable