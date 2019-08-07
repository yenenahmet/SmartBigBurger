package com.yenen.ahmet.smartbigburger.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.yenen.ahmet.smartbigburger.base.viewmodel.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding>(
    val viewModelClass: Class<VM>
) : Fragment() {

    @LayoutRes
    abstract fun getLayoutRes(): Int


    protected val viewModel by lazy {
        ViewModelProviders.of(this).get(viewModelClass)
    }

    protected var binding: DB? = null


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
    abstract fun initViewModel(viewModel: VM)


}
