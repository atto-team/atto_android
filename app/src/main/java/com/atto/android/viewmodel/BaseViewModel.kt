package com.atto.android.viewmodel

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by leekijung on 2019. 4. 21..
 */

open class BaseViewModel: ViewModel() {

    protected var compositeSubscription = CompositeDisposable()

    protected fun run(runnable: (() -> Unit)?) {
        runnable?.invoke()
    }

    fun Disposable.add(): Boolean {
        compositeSubscription.add(this)
        return false
    }
}