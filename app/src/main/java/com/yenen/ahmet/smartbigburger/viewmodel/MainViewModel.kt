package com.yenen.ahmet.smartbigburger.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yenen.ahmet.smartbigburger.adapter.ProductsAdapter
import com.yenen.ahmet.smartbigburger.base.BaseViewModel
import com.yenen.ahmet.smartbigburger.databinding.ActivityMainBinding
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.remote.ApiClient
import com.yenen.ahmet.smartbigburger.remote.BurgerService
import com.yenen.ahmet.smartbigburger.view.MainActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class MainViewModel : BaseViewModel() {

    private lateinit var adapter: ProductsAdapter
    private var items: MutableLiveData<List<ProductModel>>? = null
    private lateinit var service :BurgerService

    // View Refresh
    val handleMessage: MutableLiveData<String> = MutableLiveData()
    val handleVisibilityError :MutableLiveData<Int> = MutableLiveData()
    val handleVisibility :MutableLiveData<Int> = MutableLiveData()
    // View Refresh

    fun init(binding: ActivityMainBinding, activity: MainActivity) {
        setViewDataBinding(binding)
        adapter = ProductsAdapter(mutableListOf(), activity, binding.recyclerView)
        binding.setLifecycleOwner(activity)
        binding.searchLiveo.with(activity).searchDelay(700)
            .hideKeyboardAfterSearch()
            .minToSearch(0).build()
        service =ApiClient.createService(BurgerService::class.java,activity)
    }

    // Class Fun //
    fun getData(): LiveData<List<ProductModel>> {
        if (items == null) {
            items = MutableLiveData()
            loadData()
        }
        return items as LiveData<List<ProductModel>>
    }

    private fun loadData() {
        disposable.add(
            service.getProducts()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResults, this::handleError)
        )
    }

    private fun handleResults(response: Response<List<ProductModel>>?) {
        val body:List<ProductModel>? = response?.body()
        if (body != null && body.size > 0) {
            items?.value = body
            handleVisibility.value = View.VISIBLE
            handleVisibilityError.value = View.GONE
        } else {
            handleVisibility.value = View.GONE
            handleVisibilityError.value =View.VISIBLE
            handleMessage.value ="No Data Found"
        }
    }

    private fun handleError(t: Throwable) {
        handleVisibility.value = View.GONE
        handleVisibilityError.value = View.VISIBLE
        handleMessage.value = ApiClient.failMessage(t)
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

