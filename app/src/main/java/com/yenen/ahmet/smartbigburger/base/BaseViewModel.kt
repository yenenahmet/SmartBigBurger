package com.yenen.ahmet.smartbigburger.base

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

open class BaseViewModel<T : AppCompatActivity> : ViewModel() {

    private var dataViewBinding: ViewDataBinding? = null
    protected val disposable = CompositeDisposable()

    protected fun setViewDataBinding(viewBinding: ViewDataBinding, activity: T) {
        dataViewBinding = viewBinding
        viewBinding.setLifecycleOwner(activity)
    }

    override fun onCleared() {
        super.onCleared()
        dataViewBinding?.unbind()
        dataViewBinding = null
        disposable.dispose()
    }
}