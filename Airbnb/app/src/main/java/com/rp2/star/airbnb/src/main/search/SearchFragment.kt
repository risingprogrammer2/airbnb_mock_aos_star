package com.rp2.star.airbnb.src.main.search

import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainSearchBinding


class SearchFragment : BaseFragment<FragmentMainSearchBinding>(FragmentMainSearchBinding::bind,
    R.layout.fragment_main_search){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}