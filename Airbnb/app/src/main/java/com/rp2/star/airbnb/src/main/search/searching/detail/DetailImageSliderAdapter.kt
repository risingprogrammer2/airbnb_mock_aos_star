package com.rp2.star.airbnb.src.main.search.searching.detail

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class DetailImageSliderAdapter(searchingDetailFragment: SearchingDetailFragment,
private val lodgeImgUrlList: ArrayList<String>):
    FragmentStateAdapter(searchingDetailFragment){


    // 사진 개수
    override fun getItemCount() = lodgeImgUrlList.size

    // 해당 위치에 맞는 이미지 url을 넣은 뷰페이저 Fragment 반환
    override fun createFragment(position: Int): Fragment = DetailImageSliderFragment(lodgeImgUrlList[position])

}