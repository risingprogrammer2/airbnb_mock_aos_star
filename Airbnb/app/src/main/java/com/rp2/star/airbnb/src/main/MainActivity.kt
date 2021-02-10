package com.rp2.star.airbnb.src.main

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivityMainBinding
import com.rp2.star.airbnb.src.main.message.MessageFragment
import com.rp2.star.airbnb.src.main.my_page.MyPageFragment
import com.rp2.star.airbnb.src.main.search.SearchFragment
import com.rp2.star.airbnb.src.main.store.StoreFragment
import com.rp2.star.airbnb.src.main.trip.TripFragment


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.main_frm, MyPageFragment()).commitAllowingStateLoss()

        binding.mainBtmNav.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.menu_main_btm_nav_search -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, SearchFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_store -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, StoreFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_trip -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, TripFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_message -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MessageFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                    R.id.menu_main_btm_nav_my_page -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.main_frm, MyPageFragment())
                            .commitAllowingStateLoss()
                        return@OnNavigationItemSelectedListener true
                    }
                }
                false
            })
    }
}
