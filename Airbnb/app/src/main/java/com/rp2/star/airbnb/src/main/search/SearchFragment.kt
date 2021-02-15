package com.rp2.star.airbnb.src.main.search

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainSearchBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivity


class SearchFragment : BaseFragment<FragmentMainSearchBinding>(
    FragmentMainSearchBinding::bind,
    R.layout.fragment_main_search
){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity!!.window.apply{
            // 상태바 검정색
            statusBarColor = resources.getColor(R.color.black)
            // 상태 아이콘 흰색
            val view = decorView
            view.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            statusBarColor = Color.parseColor("#ffffff")
        }


        binding.mainSearchBtnSearch.setOnClickListener {
            val searchingIntent = Intent(activity, SearchingActivity::class.java)
            startActivity(searchingIntent)
        }
    }
}