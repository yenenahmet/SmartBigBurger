package com.yenen.ahmet.smartbigburger.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProviders
import com.yenen.ahmet.smartbigburger.base.viewmodel.BaseViewModel
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseDaggerFragment<VM : BaseViewModel, DB : ViewDataBinding>(val viewModelClass: Class<VM>) :
    DaggerFragment() {

    @Inject
    lateinit var providerFactory: AppViewModelFactory

    @LayoutRes
    abstract fun getLayoutRes(): Int

    protected var binding: DB? = null

    protected val viewModel by lazy {
        ViewModelProviders.of(this, providerFactory).get(viewModelClass)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setViewDataBinding(binding!!)
        initViewModel(viewModel)
    }

    /*
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     */
    protected abstract fun initViewModel(viewModel: VM)

}