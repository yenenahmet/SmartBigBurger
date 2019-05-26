package com.yenen.ahmet.smartbigburger.base

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel :ViewModel(){

    private var dataViewBinding: ViewDataBinding? = null
    protected val  disposable = CompositeDisposable()

    protected fun setViewDataBinding(viewBinding: ViewDataBinding) {
        dataViewBinding = viewBinding
    }

    override fun onCleared() {
        super.onCleared()
        dataViewBinding?.unbind()
        dataViewBinding = null
        disposable.dispose()
    }
}