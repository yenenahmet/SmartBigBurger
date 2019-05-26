package com.yenen.ahmet.smartbigburger.adapter

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.yenen.ahmet.smartbigburger.databinding.ItemProductLayoutBinding
import com.yenen.ahmet.smartbigburger.model.ProductModel
import com.yenen.ahmet.smartbigburger.recyclerviewhelper.BaseFilter
import com.yenen.ahmet.smartbigburger.recyclerviewhelper.RecyclerViewInputAdapter

class ProductsAdapter
    constructor(items: MutableList<ProductModel>, activity: Activity, viewToken: RecyclerView) :
    RecyclerViewInputAdapter<ProductModel, ProductsAdapter.ViewHolder>(items, activity, viewToken) , Filterable {

    private var recylerViewFilter :RecyclerViewFilter? =null


    override fun onChange(position: Int, view: View, item: ProductModel) {
        val inputView: AppCompatEditText = view as AppCompatEditText
        val text:String = inputView.text.toString().trim()
        val quantity:Int = if (text.isEmpty()) 0 else text.toInt()
        item.totalPrice = item.price * quantity
        item.quantity = quantity
        setChanged(position, item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductLayoutBinding.inflate(getInflater(parent), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
            inputConfig(holder.binding.input, position)
        }
    }

    private fun inputConfig(input: AppCompatEditText, position: Int) {
        input.setTag(position)
        input.setOnFocusChangeListener(focusChangeListener)
        input.addTextChangedListener(textWatcher)
    }

    inner class ViewHolder(val binding: ItemProductLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProductModel) {
            binding.viewadapter = item
            binding.executePendingBindings()
        }
    }


    override fun getFilter(): Filter {
        if(recylerViewFilter ==null){
            recylerViewFilter =  RecyclerViewFilter(getItems())
        }
        return recylerViewFilter as RecyclerViewFilter
    }

    fun setFilter(filter: String?) {
        if (filter != null)
            getFilter().filter("x1$filter")
    }

    inner class RecyclerViewFilter internal constructor(filterItems: List<ProductModel>) : BaseFilter<ProductModel>(filterItems) {

        override fun pubslishResults(results: List<*>) {
            setItems(results as List<ProductModel>)
        }

        override fun getFilterItem( constLowerCase: String, value: ProductModel,controlParameter: String): ProductModel? {
            when (controlParameter) {
                "x1" -> return isContainsLower(value, value.title, constLowerCase)
                else -> return null
            }
        }

    }


}