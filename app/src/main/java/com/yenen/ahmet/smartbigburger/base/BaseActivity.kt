package com.yenen.ahmet.smartbigburger.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory

abstract class BaseActivity<VM : ViewModel, DB : ViewDataBinding>(viewModelClass: Class<VM>) :
    AppCompatActivity() {

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected val binding by lazy { DataBindingUtil.setContentView(this, getLayoutRes()) as DB }

    protected val viewModel by lazy {
        val viewModelFactory:AppViewModelFactory? = getFactory()
        if (viewModelFactory !=null) {
            ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
        } else {
            ViewModelProviders.of(this).get(viewModelClass)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel(viewModel)
    }

    /*
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     */
    abstract fun initViewModel(viewModel: VM)

    /*
     Activity Use dependency injection or Not
     injection
    */
    abstract fun getFactory(): AppViewModelFactory?
}