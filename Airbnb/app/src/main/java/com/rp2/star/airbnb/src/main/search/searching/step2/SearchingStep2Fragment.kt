package com.rp2.star.airbnb.src.main.search.searching.step2

import android.os.Bundle
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingStep2Binding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView


class SearchingStep2Fragment(val searchingView: SearchingActivityView) : BaseFragment<FragmentSearchingStep2Binding>(FragmentSearchingStep2Binding::bind,
    R.layout.fragment_searching_step2){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 검색어를 보여준다
        val query = arguments?.getString("query")
        Log.d("로그","SearchingStep2 - query: $query")
        binding.searchingStep2Query.text = query

        // 둥근 모서리 적용
        binding.searchingStep2Btn1Img.clipToOutline = true
        binding.searchingStep2Btn2Img.clipToOutline = true
        binding.searchingStep2Btn3Img.clipToOutline = true

        binding.searchingStep2Btn1.setOnClickListener(btn1OnClick)
    }

    private val btn1OnClick = View.OnClickListener {
        searchingView.goToCalendar()
    }
}