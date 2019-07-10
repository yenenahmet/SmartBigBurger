package com.yenen.ahmet.smartbigburger.remote

import com.yenen.ahmet.smartbigburger.model.ProductModel
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface BurgerService {

    @Headers("Cache-Control: public, max-stale=3600")
    @GET("mobiletest1.json")
    fun getProducts(): Observable<List<ProductModel>>
}