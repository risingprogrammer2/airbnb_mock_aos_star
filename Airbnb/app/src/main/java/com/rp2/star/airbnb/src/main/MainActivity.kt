package com.rp2.star.airbnb.src.main

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
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

        ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN,
        "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MSwiaWF0IjoxNjE4MjQ2MTc1LCJleHAiOjE2NDk3ODIxNzUsInN1YiI6ImF1dGhJbmZvIn0.A8FUvBhg5zaun32-eIJuXvimvznlNnbpRUH5BEiYQsA")
            .apply()

        // 처음 화면이 켜졌을 때 활성화될 하단 버튼 - 실제 화면이 아닌 버튼에만 적용
        binding.mainBtmNav.selectedItemId = R.id.menu_main_btm_nav_my_page
        showLoadingDialog(this)

        Handler(Looper.getMainLooper()).postDelayed({
            supportFragmentManager.beginTransaction().add(R.id.main_frm, MyPageFragment()).commitAllowingStateLoss()
            dismissLoadingDialog()
        }, 1000)

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

    fun comeBackToSearch(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, SearchFragment())
            .commitAllowingStateLoss()
    }
}
