package com.rp2.star.airbnb.src.main.search.searching

import android.os.Bundle
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivitySearchingBinding
import com.rp2.star.airbnb.src.main.search.searching.step1.SearchingStep1Fragment
import com.rp2.star.airbnb.src.main.search.searching.step2.SearchingCalendarFragment
import com.rp2.star.airbnb.src.main.search.searching.step2.SearchingStep2Fragment

class SearchingActivity : BaseActivity<ActivitySearchingBinding>(ActivitySearchingBinding::inflate),
SearchingActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 1000)*/

        supportFragmentManager.beginTransaction().add(R.id.search_frm, SearchingStep1Fragment(this))
            .commitAllowingStateLoss()

    }

    // SearchingStep2Fragment로 넘어간다
    override fun goToStep2(query: String) {
        supportFragmentManager.beginTransaction().replace(R.id.search_frm,
            SearchingStep2Fragment(this).apply {
                arguments = Bundle().apply {
                    putString("query", query)
                }
            }
        ).commitAllowingStateLoss()
    }

    // 달력을 띄운다 -> 날짜 범위 선택
    override fun goToCalendar() {
        supportFragmentManager.beginTransaction().replace(R.id.search_frm,
            SearchingCalendarFragment(this)
        ).commitAllowingStateLoss()
    }
}
