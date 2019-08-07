package com.yenen.ahmet.smartbigburger.base.viewmodel

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {

    private var dataViewBinding: ViewDataBinding? = null


    fun setViewDataBinding(viewBinding: ViewDataBinding) {
        dataViewBinding = viewBinding
    }

    override fun onCleared() {
        super.onCleared()
        dataViewBinding?.unbind()
        dataViewBinding = null
    }
}