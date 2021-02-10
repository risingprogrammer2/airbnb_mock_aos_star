package com.rp2.star.airbnb.src.main.trip

import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainTripBinding


class TripFragment : BaseFragment<FragmentMainTripBinding>(FragmentMainTripBinding::bind,
    R.layout.fragment_main_trip){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}