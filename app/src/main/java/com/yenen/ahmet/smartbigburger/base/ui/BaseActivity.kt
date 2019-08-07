package com.yenen.ahmet.smartbigburger.base.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.yenen.ahmet.smartbigburger.base.viewmodel.BaseViewModel
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding>(private val viewModelClass: Class<VM>) :
    AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    protected val viewModel by lazy {
        ViewModelProviders.of(this).get(viewModelClass)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setViewDataBinding(binding)
        initViewModel(viewModel)
    }

    /*
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     */
    protected abstract fun initViewModel(viewModel: VM)


}