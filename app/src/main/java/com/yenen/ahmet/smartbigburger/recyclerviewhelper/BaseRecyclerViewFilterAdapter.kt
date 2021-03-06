package com.yenen.ahmet.smartbigburger.recyclerviewhelper

import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView

/*
    Extends alınan adapter içinde Filterable implement edilmesinin tekrarının kalkması.
    Filter için class oluşturulması
    bağımlılığını kaldırmak ve Filtreleme işlemi için yazılan adapterların
    kod kalabalığını azaltmak için oluşturulmuştur.
 */
abstract class BaseRecyclerViewFilterAdapter<T, E : RecyclerView.ViewHolder> constructor(items: MutableList<T>) :
    BaseRecyclerViewAdapter<T, E>(items), Filterable {

    private var filter: RecyclerViewFilter? = null

    // Filterable //
    override fun getFilter(): Filter {
        if (filter == null) {
            filter = RecyclerViewFilter(getItems())
        }
        return filter as RecyclerViewFilter
    }

    fun getFilterAdapter(): RecyclerViewFilter? {
        return filter
    }

    fun setFilter(filterValue: String) {
        getFilter().filter(filterValue)
    }

    inner class RecyclerViewFilter internal constructor(filterItems: List<T>) : BaseFilter<T>(filterItems) {

        override fun getFilterItem(constLowerCase: String, value: T, controlParameter: String): T? {
            if (filter != null) {
                return getRecyclerFilterItem(constLowerCase, value, controlParameter)
            }
            return null
        }

        @Suppress("UNCHECKED_CAST")
        override fun pubslishResults(results: List<*>) {
            setItems(results as List<T>)
        }
    }

    protected abstract fun getRecyclerFilterItem(constLowerCase: String, value: T, controlParameter: String): T?
    // Filterable //

    // clear Memory //
    fun unBindFilterAdapter() {
        clearFilter()
        clearItems()
    }

    protected fun clearFilter() {
        filter?.clear()
        filter = null
    }
    // clear Memory //

}