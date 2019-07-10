package com.yenen.ahmet.smartbigburger.base


import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


abstract class BaseRxSingleHandlerViewModel<T> : BaseViewModel() {

    private val disposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

    fun runObservable(observable: Observable<T>) {
        disposable.add(
            observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResults, this::handleError)
        )
    }

    abstract fun handleResults(results :T?)

    abstract fun handleError(t:Throwable)


}