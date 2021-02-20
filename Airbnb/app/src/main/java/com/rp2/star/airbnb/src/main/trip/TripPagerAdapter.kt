package com.rp2.star.airbnb.src.main.trip

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class TripPagerAdapter(tripFragment: TripFragment
): FragmentStateAdapter(tripFragment){

    private lateinit var fragment1: TripPrevFragment
    private lateinit var fragment2: TripScheduledFragment

    // 탭2개
    override fun getItemCount() = 2

    // 해당 탭에 맞는 여행 리스트를 넣어서 생성한 Fragment 반환
    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> fragment1
            else -> fragment2
        }
    }

    fun provideFragment1(prevFragment: TripPrevFragment){
        fragment1 = prevFragment
        notifyDataSetChanged()
    }

    fun provideFragment2(scheduledFragment: TripScheduledFragment){
        fragment2 = scheduledFragment
        notifyDataSetChanged()
    }
}