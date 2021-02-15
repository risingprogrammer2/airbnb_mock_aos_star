package com.rp2.star.airbnb.src.main.search.searching.step2

import android.os.Bundle
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingStep2Binding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView


class SearchingStep2Fragment(val searchingView: SearchingActivityView) : BaseFragment<FragmentSearchingStep2Binding>(FragmentSearchingStep2Binding::bind,
    R.layout.fragment_searching_step2){

    private val sp = ApplicationClass.sSharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색어를 보여준다
        val query = arguments?.getString("query")
        val query2 = sp.getString("query",null)
        Log.d("로그","SearchingStep2 - query: $query")
        Log.d("로그","SearchingStep2 - query2: $query2")
        binding.searchingStep2Query.text = query

        // 둥근 모서리 적용
        binding.searchingStep2Btn1Img.clipToOutline = true
        binding.searchingStep2Btn2Img.clipToOutline = true
        binding.searchingStep2Btn3Img.clipToOutline = true

        binding.searchingStep2Btn1.setOnClickListener(btn1OnClick)

        // 뒤로가기 버튼
        binding.searchingStep2Back.setOnClickListener {
            searchingView.goToStep1()
        }
    }

    private val btn1OnClick = View.OnClickListener {
        searchingView.goToCalendar()
    }
}