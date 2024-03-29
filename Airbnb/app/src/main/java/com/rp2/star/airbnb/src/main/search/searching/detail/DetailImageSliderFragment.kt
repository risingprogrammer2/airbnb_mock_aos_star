package com.rp2.star.airbnb.src.main.search.searching.detail

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentDetailImageSliderBinding


class DetailImageSliderFragment(private val imgUrl: String) :
    BaseFragment<FragmentDetailImageSliderBinding>(FragmentDetailImageSliderBinding::bind,
    R.layout.fragment_detail_image_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context!!)
            .load(imgUrl)
            .error(R.drawable.search_main_seoul)
            .into(binding.detailSliderImg)
    }
}