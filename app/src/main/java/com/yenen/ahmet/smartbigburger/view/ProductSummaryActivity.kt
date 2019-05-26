package com.yenen.ahmet.smartbigburger.view

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.yenen.ahmet.smartbigburger.R
import com.yenen.ahmet.smartbigburger.base.BaseActivity
import com.yenen.ahmet.smartbigburger.databinding.ActivityProductSummaryBinding
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.viewmodel.ProductSummaryViewModel

class ProductSummaryActivity : BaseActivity<ProductSummaryViewModel,ActivityProductSummaryBinding>(ProductSummaryViewModel::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retVal:ArrayList<ProductModel> = intent.getParcelableArrayListExtra<ProductModel>("extra_products")
        observe(viewModel.getLoadData(retVal))
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Summary")
    }

    fun observe(data : LiveData<List<ProductModel>>){
        data.observe(this, Observer { t->
            if(t!=null){
                viewModel.getAdapter().setItems(t)
            }
        })
    }
    //  Base Activity //
    override fun getLayoutRes(): Int {
        return  R.layout.activity_product_summary
    }

    override fun initViewModel(viewModel: ProductSummaryViewModel) {
        binding.viewModel = viewModel
        viewModel.init(binding,this)
    }
    //  Base Activity //


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId){
            android.R.id.home ->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
