package com.rp2.star.airbnb.src.main.trip

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentTripScheduledBinding
import com.rp2.star.airbnb.src.main.trip.models.ScheduleResult


class TripScheduledFragment(private val tripList: ArrayList<ScheduleResult>) :
    BaseFragment<FragmentTripScheduledBinding>(
        FragmentTripScheduledBinding::bind,
    R.layout.fragment_trip_scheduled) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tripRecyclerAdapter = TripRecyclerAdapter(context!!, tripList)
        binding.tripPrevRecyclerView.adapter = tripRecyclerAdapter
        binding.tripPrevRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)

    }
}