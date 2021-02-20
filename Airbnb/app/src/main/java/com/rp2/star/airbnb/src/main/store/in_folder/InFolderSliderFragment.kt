package com.rp2.star.airbnb.src.main.store.in_folder

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentShowRecyclerViewSliderBinding


class InFolderSliderFragment(private val imgUrl: String) :
    BaseFragment<FragmentShowRecyclerViewSliderBinding>(FragmentShowRecyclerViewSliderBinding::bind,
    R.layout.fragment_show_recycler_view_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context!!)
            .load(imgUrl)
            .into(binding.showSliderImg)
    }

}