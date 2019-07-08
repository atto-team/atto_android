package com.atto.android.controller.activity

import android.os.Bundle
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by leekijung on 2019. 7. 9..
 */
abstract class BaseActivity : RxAppCompatActivity() {
    protected val detachDisposable: CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupStartWindowAnimator()
    }

    override fun finish() {
        super.finish()
        setupEndWindowAnimator()
    }

    protected fun setupStartWindowAnimator() {
        //overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    protected fun setupEndWindowAnimator() {
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }

    override fun onDestroy() {
        super.onDestroy()
        detachDisposable.clear()
    }

    fun Disposable.add(): Boolean {
        detachDisposable.add(this)
        return false
    }
}
