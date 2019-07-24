package com.yenen.ahmet.smartbigburger.base

import androidx.recyclerview.widget.RecyclerView
import com.yenen.ahmet.smartbigburger.recyclerviewhelper.BaseRecyclerViewAdapter


// T ->  Service Results variable
// M ->  List<M>  M == item
// A ->  Adapter
abstract class BaseRxHandlerAndAdapterViewModel<T,M, A : BaseRecyclerViewAdapter<M,*>>
constructor(
    noDataFoundText: String,
    adapter: A
) :
    BaseFullRxSingleHandlerViewModel<T>(noDataFoundText) {


    private val adapter: A

    init {
        this.adapter = adapter
    }


    fun setItemsAdapter(items: List<M>) {
        adapter.setItems(items)
    }

    fun getAdapter(): A {
        return adapter
    }

    override fun onCleared() {
        super.onCleared()
        adapter.clearItems()
    }
}