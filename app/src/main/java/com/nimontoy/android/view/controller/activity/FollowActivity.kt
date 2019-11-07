package com.nimontoy.android.view.controller.activity

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.google.android.material.tabs.TabLayout
import com.nimontoy.android.R
import com.nimontoy.android.view.adapter.AttoPagerAdapter
import com.nimontoy.android.view.controller.fragment.mypage.FollowFragment
import com.nimontoy.android.view.controller.fragment.mypage.FollowerFragment
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.android.synthetic.main.activity_follow.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class FollowActivity :BaseActivity() {

    private val fmMgr: FragmentManager by lazy { supportFragmentManager }
    private val backButtonSubject = BehaviorSubject.createDefault(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_follow)

        initViews()
        setupViewPager()
    }

    private fun initViews() {
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
        follow_viewpager.adapter = AttoPagerAdapter(
            fmMgr, listOf(
                FollowFragment.newInstance(),
                FollowerFragment.newInstance()
            ), listOf(
                "FOLLOW",
                "FOLLOWER"
            )
        )
        val tab = findViewById<TabLayout>(R.id.follow_tab)
        follow_viewpager.isPagingEnabled = false
        tab.setupWithViewPager(follow_viewpager)
        follow_viewpager.offscreenPageLimit = navigation.itemCount
    }
}