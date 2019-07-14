package com.atto.android.controller.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.atto.android.R
import com.atto.android.controller.fragment.home.HomeFragment
import com.atto.android.controller.fragment.mypage.MypageFragment
import com.atto.android.controller.fragment.notification.NotificationFragment
import com.atto.android.controller.fragment.shop.ShopFragment
import com.atto.android.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

/**
 * Created by leekijung on 2019. 7. 9..
 */
class MainActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }
    private val fmMgr: FragmentManager by lazy { supportFragmentManager }
    private lateinit var ft: FragmentTransaction

    private lateinit var fragment: Fragment

    private val backButtonSubject = BehaviorSubject.createDefault(0L)

    private val onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    fragment = HomeFragment.newInstance()
                    replaceFragment(fragment, getString(R.string.home_tab))
                    return true
                }
                R.id.navigation_notification -> {
                    fragment = NotificationFragment.newInstance()
                    replaceFragment(fragment, getString(R.string.notification_tab))
                    return true
                }
                R.id.navigation_mypage -> {
                    fragment = MypageFragment.newInstance()
                    replaceFragment(fragment, getString(R.string.mypage_tab))
                    return true
                }
                R.id.navigation_shop -> {
                    fragment = ShopFragment.newInstance()
                    replaceFragment(fragment, getString(R.string.shop_tab))
                    return true
                }
            }
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initFragment()
    }

    private fun initViews() {
        /*navigation.enableAnimation(false)
        navigation.enableItemShiftingMode(false)
        navigation.enableShiftingMode(false)*/
        navigation.onNavigationItemSelectedListener = onNavigationItemSelectedListener

        backButtonSubject.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .subscribe { t ->
                if (t[1] - t[0] <= 1500) {
                    finish()
                } else {
                    toast("뒤로가기 버튼을 한번 더 누르면 종료합니다")
                }
            }.add()
    }

    private fun initFragment() {
        ft = fmMgr.beginTransaction()
        ft.addToBackStack(getString(R.string.home_tab))

        fragment = HomeFragment.newInstance()
        ft.add(R.id.menu_fragment, fragment, getString(R.string.home_tab)).commit()
        navigation.selectedItemId = R.id.navigation_home
    }

    fun replaceFragment(fm: Fragment, fmTag: String) {
        ft = fmMgr.beginTransaction().addToBackStack(null)
        hideAllFragment()
        val fragment = fmMgr.findFragmentByTag(fmTag)
        if (fragment == null) ft.add(R.id.menu_fragment, fm, fmTag)
        else ft.show(fragment)
        ft.commit()
    }

    private fun hideAllFragment() {
        fmMgr.fragments.forEach { ft.hide(it) }
    }

    override fun onBackPressed() {
        backButtonSubject.onNext(System.currentTimeMillis())
    }
}
