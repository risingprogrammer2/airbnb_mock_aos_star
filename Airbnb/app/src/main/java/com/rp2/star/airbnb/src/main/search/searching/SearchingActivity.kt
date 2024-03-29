package com.rp2.star.airbnb.src.main.search.searching

import SearchingDetailCalendarFragment
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivitySearchingBinding
import com.rp2.star.airbnb.src.main.search.searching.calendar.SearchingCalendarFragment
import com.rp2.star.airbnb.src.main.search.searching.card.SearchingCardFragment
import com.rp2.star.airbnb.src.main.search.searching.company.SearchingCompanyFragment
import com.rp2.star.airbnb.src.main.search.searching.company.SearchingReviewFragment
import com.rp2.star.airbnb.src.main.search.searching.company2.SearchingCompany2Fragment
import com.rp2.star.airbnb.src.main.search.searching.detail.SearchingDetailFragment
import com.rp2.star.airbnb.src.main.search.searching.pay.SearchingPayFragment
import com.rp2.star.airbnb.src.main.search.searching.show.SearchingShowFragment
import com.rp2.star.airbnb.src.main.search.searching.step1.SearchingStep1Fragment
import com.rp2.star.airbnb.src.main.search.searching.step2.SearchingStep2Fragment

class SearchingActivity : BaseActivity<ActivitySearchingBinding>(ActivitySearchingBinding::inflate),
SearchingActivityView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 1000)*/

        // 상태 아이콘 흰색
        val mWindow = window
        val mView = window.decorView
        mView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mWindow.statusBarColor = Color.parseColor("#ffffff")


        supportFragmentManager.beginTransaction().add(R.id.searching_frm, SearchingShowFragment(this))
            .commitAllowingStateLoss()
        /*supportFragmentManager.beginTransaction().add(R.id.searching_frm, SearchingCompanyFragment(this))
            .commitAllowingStateLoss()*/

    }

    /*
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
*/
    override fun goToStep1() {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingStep1Fragment(this)
        ).commitAllowingStateLoss()
    }

    // SearchingStep2Fragment로 넘어간다
    override fun goToStep2() {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingStep2Fragment(this)
        ).commitAllowingStateLoss()
}

    // 달력을 띄운다 -> 날짜 범위 선택
    override fun goToCalendar() {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingCalendarFragment(this)
        ).commitAllowingStateLoss()
    }

    // 날짜 선택 후 일행 선택으로 이동
    override fun goToCompany() {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingCompanyFragment(this)
        ).commitAllowingStateLoss()
    }

    // 검색 결과 화면으로 이동
    override fun goToShow() {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
        SearchingShowFragment(this)
        ).commitAllowingStateLoss()
    }

    // 상세 정보 화면으로 이동
    override fun goToDetail(lodgeId: Int) {

        // 숙소 id를 해당 프래그먼트에 넘겨준다
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingDetailFragment(this).apply {
                arguments = Bundle().apply {
                    putInt("lodgeId", lodgeId)
                }
            }
        ).commitAllowingStateLoss()
    }

    override fun goToReview() {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingReviewFragment(this)
        ).commitAllowingStateLoss()
    }

    override fun goToDetailCalendar(lodgeId: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingDetailCalendarFragment(this).apply{
                arguments = Bundle().apply{
                    putInt("lodgeId", lodgeId)
                }
            }
        ).commitAllowingStateLoss()
    }

    override fun goToPay(lodgeId: Int) {
        supportFragmentManager.beginTransaction().replace(R.id.searching_frm,
            SearchingPayFragment(this).apply {
                arguments = Bundle().apply {
                    putInt("lodgeId", lodgeId)

                }
            }
        ).commitAllowingStateLoss()
    }

    // Bottom Sheet
    override fun goToCard() {
        val bottomSheetCard = SearchingCardFragment(this)
        bottomSheetCard.show(supportFragmentManager, "adding card info")
    }

    override fun goToCompany2() {
        val bottomSheetGuest = SearchingCompany2Fragment(this)
        bottomSheetGuest.show(supportFragmentManager, "changing guests info")
    }

    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        val parentFragment = fragmentManager.findFragmentById(
            resources.getIdentifier("searching_show_root", "id", packageName)
        )

        if (parentFragment != null && parentFragment.childFragmentManager.backStackEntryCount > 0) {
            parentFragment.childFragmentManager.popBackStack()
        } else {
            //goToStep1()
            super.onBackPressed()
        }
    }
}
