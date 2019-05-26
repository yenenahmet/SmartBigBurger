package com.yenen.ahmet.smartbigburger.recyclerviewhelper

import android.widget.Filter

abstract class BaseFilter<T> protected constructor(filterItems: List<T>) : Filter() {
    private var allItems: MutableList<T>? =null
    private val mLock = Any()

    init {
        this.allItems = filterItems as MutableList<T>
    }

    override protected fun performFiltering(constraint: CharSequence?): FilterResults {
        synchronized(mLock) {
            val results = FilterResults()
            if (constraint != null && constraint.length > 2) {
                val list: MutableList<T> = mutableListOf()
                val constLowerCase = constraint.toString().toLowerCase()
                val controlParameter = constLowerCase.substring(0, 2)
                val lowerCase = constLowerCase.substring(2, constLowerCase.length)
                for (values in allItems!!) {
                    if (values != null) {
                        val model = getFilterItem(lowerCase, values, controlParameter)
                        if (model != null) {
                            list.add(model)
                        }
                    }
                }
                results.values = list
                results.count = list.size
            } else {
                results.values = allItems
                results.count = allItems?.size!!
            }
            return results
        }
    }

    override protected fun publishResults(constraint: CharSequence, results: FilterResults?) {
        synchronized(mLock) {
            if (results != null && results.values != null) {
                val arrayList: List<*> = results.values as List<*>
                pubslishResults(arrayList )
            }
        }
    }


    protected abstract fun pubslishResults(results: List<*>)

    protected abstract fun getFilterItem(constLowerCase: String, value: T, controlParameter: String): T?

    fun clear() {
        allItems?.clear()
    }


    protected fun isContainsLower(model: T, value: String, constLowerCase: String): T? {
        return if (value.toLowerCase().contains(constLowerCase)) {
            model
        } else null
    }


}
