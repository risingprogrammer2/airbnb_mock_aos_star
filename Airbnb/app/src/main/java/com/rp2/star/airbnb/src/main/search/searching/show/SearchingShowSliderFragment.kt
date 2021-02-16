package com.rp2.star.airbnb.src.main.search.searching.show

import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentDetailImageSliderBinding


class SearchingShowSliderFragment(private val imgUrl: String) :
    BaseFragment<FragmentDetailImageSliderBinding>(FragmentDetailImageSliderBinding::bind,
    R.layout.fragment_detail_image_slider) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(context!!)
            .load(imgUrl)
            .into(binding.detailSliderImg)
    }

}