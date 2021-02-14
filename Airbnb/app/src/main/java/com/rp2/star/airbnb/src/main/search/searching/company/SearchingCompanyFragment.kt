package com.rp2.star.airbnb.src.main.search.searching.step1

import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingCompanyBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView


class SearchingCompanyFragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingCompanyBinding>(FragmentSearchingCompanyBinding::bind,
    R.layout.fragment_searching_company){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}