package com.atto.android.controller.activity

import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.atto.android.R
import com.atto.android.viewmodel.MainViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Created by leekijung on 2019. 7. 9..
 */
class MainActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProviders.of(this).get(MainViewModel::class.java) }

    private val fmMgr: FragmentManager by lazy { supportFragmentManager }
    private val ft: FragmentTransaction by lazy { fmMgr.beginTransaction() }
    private lateinit var fragment: Fragment

    private val onNavigationItemSelectedListener = object: BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            when (item.itemId) {
                R.id.navigation_collection -> {
                    return true
                }
                R.id.navigation_notification -> {
                    return true
                }
                R.id.navigation_mypage -> {
                    return true
                }
                R.id.navigation_shop -> {
                    return true
                }
            }
            return false
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
