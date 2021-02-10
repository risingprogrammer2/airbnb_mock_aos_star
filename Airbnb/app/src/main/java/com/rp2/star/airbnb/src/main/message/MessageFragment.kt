package com.rp2.star.airbnb.src.main.message

import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainMessageBinding


class MessageFragment : BaseFragment<FragmentMainMessageBinding>(FragmentMainMessageBinding::bind,
    R.layout.fragment_main_message){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}