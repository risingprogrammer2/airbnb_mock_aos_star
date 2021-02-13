package com.rp2.star.airbnb.src.main.search.searching

import android.os.Bundle
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivitySearchingBinding
import com.rp2.star.airbnb.src.main.search.searching.step1.SearchingStep1Fragment

class SearchingActivity : BaseActivity<ActivitySearchingBinding>(ActivitySearchingBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().add(R.id.search_frm, SearchingStep1Fragment())
            .commitAllowingStateLoss()
        /*Handler(Looper.getMainLooper()).postDelayed({
            dismissLoadingDialog()
        }, 1000)*/
    }

}
