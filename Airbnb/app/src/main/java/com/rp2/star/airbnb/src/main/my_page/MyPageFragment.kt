package com.rp2.star.airbnb.src.main.my_page

import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainMyPageBinding
import com.rp2.star.airbnb.databinding.FragmentMainMyPageBinding.bind


class MyPageFragment : BaseFragment<FragmentMainMyPageBinding>(FragmentMainMyPageBinding::bind,
    R.layout.fragment_main_my_page){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}