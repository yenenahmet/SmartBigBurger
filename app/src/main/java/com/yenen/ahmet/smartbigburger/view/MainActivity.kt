package com.yenen.ahmet.smartbigburger.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import br.com.liveo.searchliveo.SearchLiveo
import com.yenen.ahmet.smartbigburger.R
import com.yenen.ahmet.smartbigburger.base.BaseActivity
import com.yenen.ahmet.smartbigburger.databinding.ActivityMainBinding
import com.yenen.ahmet.smartbigburger.factory.AppViewModelFactory
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.viewmodel.MainViewModel
import dagger.android.AndroidInjection
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(MainViewModel::class.java),
    SearchLiveo.OnSearchListener {

    @Inject
    lateinit var viewModelFactorty: AppViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setTitle("Big Burger")
        observeData()
    }

    // BaseActivity MVVM //
    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initViewModel(viewModel: MainViewModel) {
        binding.viewModel = viewModel
        viewModel.init(binding, this)
        binding.lifecycleOwner =this
    }

    override fun getFactory(): AppViewModelFactory? {
        return viewModelFactorty
    }

    // BaseActivity MVVM //

    //Class Fun //
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_search -> {
                binding.searchLiveo.show();
            }
            R.id.next -> {
                viewModel.getAdapter()?.clearFocusView()
                val products = viewModel.filterForQuantity()
                if (products != null && !products.isEmpty()) {
                    val intent = Intent(this, ProductSummaryActivity::class.java)
                    intent.putParcelableArrayListExtra(
                        "extra_products",
                        products as ArrayList<ProductModel>
                    )
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Please Select Product !",Toast.LENGTH_LONG).show()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
    //Class Fun //

    // LiveData Listener //
    fun observeData() {
        viewModel.getData().observe(this, Observer { t ->
            if (t != null) {
                viewModel.getAdapter()?.setItems(t)
            }
        })
    }
    // LiveData Listener //


    // SearchView //
    override fun changedSearch(p0: CharSequence?) {
        if (p0 != null) {
            if (p0.isEmpty() && viewModel.getAdapter()?.clearFocusView()!!) {
                viewModel.hideKeybord()
                binding.searchLiveo.hide()
            }
            viewModel.filter(p0.toString())
        }
    }
    // SearchView //

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null && requestCode == SearchLiveo.REQUEST_CODE_SPEECH_INPUT) {
            binding.searchLiveo.resultVoice(requestCode, resultCode, data)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.hideKeybord()
    }

}
