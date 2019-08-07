package com.yenen.ahmet.smartbigburger.base.ui

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.yenen.ahmet.smartbigburger.base.viewmodel.BaseFullRxSingleHandlerViewModel
import com.yenen.ahmet.smartbigburger.base.viewmodel.BaseViewModel

abstract class BaseRemoteDaggerFragment<VM : BaseViewModel, DB : ViewDataBinding, T>
constructor(viewModelClass: Class<VM>) : BaseDaggerFragment<VM, DB>(viewModelClass) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel is BaseFullRxSingleHandlerViewModel<*>) {
            val castViewModel = viewModel as BaseFullRxSingleHandlerViewModel<T>
            controlDataObserve(castViewModel)
            controlErrObserve(castViewModel)
            controlNoDataFoundObserve(castViewModel)
        }
    }

    private fun controlDataObserve(viewModel: BaseFullRxSingleHandlerViewModel<T>) {
        runLoading()
        viewModel.getData().observe(this, Observer {
            it?.let {
                onDataObserve(it)
                closeLoading()
            }
        })
    }


    private fun controlErrObserve(viewModel: BaseFullRxSingleHandlerViewModel<T>) {
        viewModel.errMessage.observe(this, Observer {
            it?.let {
                onErrorObserve(it)
                closeLoading()
            }
        })
    }

    private fun controlNoDataFoundObserve(viewModel: BaseFullRxSingleHandlerViewModel<T>) {
        viewModel.noDataFound.observe(this, Observer {
            it?.let {
                onNoDataFound(it)
                closeLoading()
            }
        })
    }

    @Suppress("UNCHECKED_CAST")
    protected fun runDataChangeable(){
        if (viewModel is BaseFullRxSingleHandlerViewModel<*>) {
            runLoading()
            val castViewModel = viewModel as BaseFullRxSingleHandlerViewModel<T>
            castViewModel.dataChangeable()
        }
    }

    // Abstract //
    protected abstract fun onDataObserve(resultsObserve: T)

    protected abstract fun onErrorObserve(errorMessage: String)

    protected abstract fun onNoDataFound(noDataFoundMessage: String)

    protected abstract fun runLoading()

    protected abstract fun closeLoading()
}