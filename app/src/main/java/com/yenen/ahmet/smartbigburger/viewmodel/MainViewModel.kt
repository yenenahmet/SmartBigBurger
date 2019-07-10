package com.yenen.ahmet.smartbigburger.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yenen.ahmet.smartbigburger.adapter.ProductsAdapter
import com.yenen.ahmet.smartbigburger.base.BaseRxSingleHandlerViewModel
import com.yenen.ahmet.smartbigburger.databinding.ActivityMainBinding
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.remote.ApiClient
import com.yenen.ahmet.smartbigburger.remote.BurgerService
import com.yenen.ahmet.smartbigburger.view.MainActivity
import javax.inject.Inject

class MainViewModel @Inject constructor(private val burgerService: BurgerService)
: BaseRxSingleHandlerViewModel<List<ProductModel>>() {

    private lateinit var adapter: ProductsAdapter
    private var items: MutableLiveData<List<ProductModel>>? = null

    // View Refresh
    val handleMessage: MutableLiveData<String> = MutableLiveData()
    val handleVisibilityError :MutableLiveData<Int> = MutableLiveData()
    val handleVisibility :MutableLiveData<Int> = MutableLiveData()
    // View Refresh

    fun init(binding: ActivityMainBinding, activity: MainActivity) {
        setViewDataBinding(binding)
        adapter = ProductsAdapter(mutableListOf(), activity, binding.recyclerView)
        binding.searchLiveo.with(activity).searchDelay(700)
            .hideKeyboardAfterSearch()
            .minToSearch(0).build()
    }


    override fun handleError(t: Throwable) {
        handleVisibility.value = View.GONE
        handleVisibilityError.value = View.VISIBLE
        handleMessage.value = ApiClient.failMessage(t)
    }


    override fun handleResults(results: List<ProductModel>?) {
        if (results != null && results.size > 0) {
            items?.value = results
            handleVisibility.value = View.VISIBLE
            handleVisibilityError.value = View.GONE
        } else {
            handleVisibility.value = View.GONE
            handleVisibilityError.value =View.VISIBLE
            handleMessage.value ="No Data Found"
        }
    }

    // Class Fun //
    fun getData(): LiveData<List<ProductModel>> {
        if (items == null) {
            items = MutableLiveData()
            runObservable(burgerService.getProducts())
        }
        return items as LiveData<List<ProductModel>>
    }


    fun getAdapter(): ProductsAdapter? {
        return adapter
    }

    fun filter(text: String) {
        adapter.setFilter("x1"+text)
    }

    fun hideKeybord() {
        adapter.hideKeybord()
    }

    fun filterForQuantity(): List<ProductModel>? {
        return items?.value?.filter { s -> s.quantity > 0 }
    }

    override fun onCleared() {
        super.onCleared()
        adapter.unBind()
    }
    // Class Fun //
}

