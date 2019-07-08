package com.atto.android.controller.fragment

import android.app.Activity
import android.content.Context
import com.atto.android.AttoApplication
import com.trello.rxlifecycle2.components.support.RxFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by leekijung on 2019. 7. 9..
 */
open class BaseFragment : RxFragment() {
    protected val detachDisposable: CompositeDisposable = CompositeDisposable()

    protected var application: AttoApplication? = null
    protected var activityContext: Context? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            activityContext = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        activityContext = null
        detachDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()
        application = null
    }

    fun Disposable.add(): Boolean {
        detachDisposable.add(this)
        return false
    }
}
