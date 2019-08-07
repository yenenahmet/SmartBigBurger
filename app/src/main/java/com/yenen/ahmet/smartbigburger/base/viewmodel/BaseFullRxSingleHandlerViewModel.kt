package com.yenen.ahmet.smartbigburger.base.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yenen.ahmet.smartbigburger.remote.ApiClient
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

abstract class BaseFullRxSingleHandlerViewModel<T> : BaseViewModel() {

    private val disposable = CompositeDisposable()
    private var resultsLiveData: MutableLiveData<T>? = null

    val errMessage = MutableLiveData<String>()
    val noDataFound = MutableLiveData<String>()

    fun getData(): LiveData<T> {
        if (resultsLiveData == null) {
            resultsLiveData = MutableLiveData()
            runObservable(getServiceObservable())
        }
        return resultsLiveData as LiveData<T>
    }

    fun dataChangeable() {
        runObservable(getServiceObservable())
    }

    protected fun getResultsValue():T?{
        return resultsLiveData?.value
    }

    protected abstract fun getServiceObservable(): Observable<T>

    protected abstract fun getNoDataFoundText():String

    // Private Fun //
    private fun runObservable(observable: Observable<T>) {
        disposable.add(
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResults, this::handleError)
        )
    }

    private fun handleResults(results: T?) {
        if (results != null) {
            if (results is List<*>) {
                if (!results.isEmpty()) {
                    resultsLiveData?.value = results
                } else {
                    noDataFound.value = getNoDataFoundText()
                }
            } else {
                resultsLiveData?.value = results
            }
        } else {
            noDataFound.value = getNoDataFoundText()
        }
    }

    private fun handleError(t: Throwable) {
        errMessage.value = ApiClient.failMessage(t)
    }
    // Private Fun //

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}