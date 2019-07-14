package com.atto.android.controller.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.atto.android.R
import com.atto.android.adapter.AttoPagerAdapter
import com.atto.android.controller.fragment.home.HomeFragment
import com.atto.android.controller.fragment.mypage.MypageFragment
import com.atto.android.controller.fragment.notification.NotificationFragment
import com.atto.android.controller.fragment.shop.ShopFragment
import com.atto.android.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
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

    private val backButtonSubject = BehaviorSubject.createDefault(0L)

    private val onNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_home -> {
                    view_pager.currentItem = 0
                    return true
                }
                R.id.navigation_notification -> {
                    view_pager.currentItem = 1
                    return true
                }
                R.id.navigation_mypage -> {
                    view_pager.currentItem = 2
                    return true
                }
                R.id.navigation_shop -> {
                    view_pager.currentItem = 3
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
        setupViewPager()
    }

    private fun initViews() {
        navigation.enableAnimation(false)
        navigation.enableItemShiftingMode(false)
        navigation.enableShiftingMode(false)
        navigation.onNavigationItemSelectedListener = onNavigationItemSelectedListener

        backButtonSubject.toFlowable(BackpressureStrategy.BUFFER)
            .observeOn(AndroidSchedulers.mainThread())
            .buffer(2, 1)
            .subscribe { t ->
                if (t[1] - t[0] <= 1500) {
                    finish()
                } else {
                    toast(R.string.terminate_app_with_click_twice)
                }
            }.add()
    }

    private fun setupViewPager() {
        view_pager.adapter = AttoPagerAdapter(
            fmMgr, listOf(
                HomeFragment.newInstance(),
                NotificationFragment.newInstance(),
                MypageFragment.newInstance(),
                ShopFragment.newInstance()
            ), listOf(
                getString(R.string.notification_tab),
                getString(R.string.notification_tab),
                getString(R.string.mypage_tab),
                getString(R.string.shop_tab)
            )
        )
        view_pager.isPagingEnabled = false
        navigation.setupWithViewPager(view_pager)
        view_pager.offscreenPageLimit = navigation.itemCount
        navigation.selectedItemId = R.id.navigation_home
    }

    override fun onBackPressed() {
        backButtonSubject.onNext(System.currentTimeMillis())
    }
}
