package com.yenen.ahmet.smartbigburger.base.viewmodel

import com.yenen.ahmet.smartbigburger.recyclerviewhelper.BaseRecyclerViewAdapter


// T ->  Service Results variable
// M ->  List<M>  M == item
// A ->  Adapter
abstract class BaseRxHandlerAndAdapterViewModel<T,M, A : BaseRecyclerViewAdapter<M,*>>
constructor(
    private val adapter: A
) :
    BaseFullRxSingleHandlerViewModel<T>() {

    fun setItemsAdapter(items: List<M>) {
        if(!items.isEmpty()){
            adapter.setItems(items)
        }else{
            noDataFound.value = getNoDataFoundText()
        }

    }

    fun getAdapter(): A {
        return adapter
    }

}