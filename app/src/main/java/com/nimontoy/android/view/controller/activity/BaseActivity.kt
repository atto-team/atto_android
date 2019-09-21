package com.nimontoy.android.view.controller.activity

import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.nimontoy.android.AttoApplication
import com.nimontoy.android.R
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
        AttoApplication.activity = this

        checkStatusBarColor()
        setupStartWindowAnimator()
    }

    override fun finish() {
        super.finish() 
        setupEndWindowAnimator()
    }

    private fun checkStatusBarColor() {
        //status bar color
        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = ContextCompat.getColor(this, R.color.colorDark)
        }
    }

    protected fun setupStartWindowAnimator() {
        //overridePendingTransition(R.anim.start_enter, R.anim.start_exit);
    }

    protected fun setupEndWindowAnimator() {
        //overridePendingTransition(R.anim.end_enter, R.anim.end_exit);
    }

    override fun onDestroy() {
        detachDisposable.clear()
        super.onDestroy()
    }

    fun Disposable.add(): Boolean {
        detachDisposable.add(this)
        return false
    }
}
