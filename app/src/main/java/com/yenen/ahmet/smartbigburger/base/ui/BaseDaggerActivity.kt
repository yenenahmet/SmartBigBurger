package com.yenen.ahmet.smartbigburger.base.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.yenen.ahmet.smartbigburger.base.viewmodel.BaseViewModel
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseDaggerActivity<VM : BaseViewModel, DB : ViewDataBinding>(
    val viewModelClass: Class<VM>
) : DaggerAppCompatActivity() {

    @Inject
    lateinit var factory: AppViewModelFactory

    @LayoutRes
    abstract fun getLayoutRes(): Int


    protected val binding by lazy {
        DataBindingUtil.setContentView(this, getLayoutRes()) as DB
    }

    protected val viewModel by lazy {
        ViewModelProviders.of(this, factory).get(viewModelClass)
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
    abstract fun initViewModel(viewModel: VM)


}