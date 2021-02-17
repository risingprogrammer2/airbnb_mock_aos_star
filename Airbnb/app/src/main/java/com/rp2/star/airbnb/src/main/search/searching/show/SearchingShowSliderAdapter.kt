package com.rp2.star.airbnb.src.main.search.searching.show

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rp2.star.airbnb.src.main.search.searching.detail.DetailImageSliderFragment

class SearchingShowSliderAdapter(searchingShowFragment: SearchingShowFragment,
                                 private val lodgeImgUrlList: ArrayList<String>):
    FragmentStateAdapter(searchingShowFragment){

    // 사진 개수
    override fun getItemCount() = lodgeImgUrlList.size

    // 해당 위치에 맞는 이미지 url을 넣은 뷰페이저 Fragment 반환
    override fun createFragment(position: Int): Fragment = DetailImageSliderFragment(lodgeImgUrlList[position])

}