package com.yenen.ahmet.smartbigburger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yenen.ahmet.smartbigburger.adapter.ProductSummaryAdapter
import com.yenen.ahmet.smartbigburger.base.BaseViewModel
import com.yenen.ahmet.smartbigburger.databinding.ActivityProductSummaryBinding
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.view.ProductSummaryActivity

class ProductSummaryViewModel :BaseViewModel(){

    private lateinit var adapter :ProductSummaryAdapter
    private var items: MutableLiveData<List<ProductModel>>? = null

    fun init(binding: ActivityProductSummaryBinding, activity: ProductSummaryActivity){
        setViewDataBinding(binding)
        adapter = ProductSummaryAdapter(mutableListOf())
        binding.setLifecycleOwner(activity)
    }


    fun getAdapter(): ProductSummaryAdapter{
        return adapter
    }

    fun getLoadData(products : List<ProductModel>) : LiveData<List<ProductModel>> {
        if(items ==null){
            items = MutableLiveData()
            items?.value =products
        }
        return  items as LiveData<List<ProductModel>>
    }

    fun getTotal():String{
        val retVal =  items?.value?.map { it.totalPrice }?.sum()?.div(100)
        val valObject  = if(retVal==null) 0.0 else retVal
        return  valObject.toString()+ " ₺"
    }

}