package com.yenen.ahmet.smartbigburger.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import br.com.liveo.searchliveo.SearchLiveo
import com.yenen.ahmet.smartbigburger.R
import com.yenen.ahmet.smartbigburger.base.BaseActivity
import com.yenen.ahmet.smartbigburger.databinding.ActivityProductSummaryBinding
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.viewmodel.ProductSummaryViewModel
import dagger.android.AndroidInjection

class ProductSummaryActivity : BaseActivity<ProductSummaryViewModel,ActivityProductSummaryBinding>(ProductSummaryViewModel::class.java)
    ,SearchLiveo.OnSearchListener{


    override fun getFactory(): AppViewModelFactory? {
        return null
    }

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
            R.id.menu_search -> {
                binding.searchLiveo.show();
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.product_summary_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun changedSearch(p0: CharSequence?) {
        if (p0 != null) {
            viewModel.filter(p0.toString())
        }
    }

}
