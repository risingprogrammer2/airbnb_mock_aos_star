package com.rp2.star.airbnb.src.main.search.searching.step2

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingCalendarBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.calendar.AppBarStateChangeListener


class SearchingCalendarFragment(private val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingCalendarBinding>(FragmentSearchingCalendarBinding::bind
        ,R.layout.fragment_searching_calendar){

    private var mStartDate: String? = null
    private var mEndDate: String? = null
    private var mStartMonth: String? = null
    private var mEndMonth: String? = null
    private var mStartYear: String? = null
    private var mEndYear: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchingCalendarLayoutTop.clipToOutline = true

        binding.searchingCalendarAppbar.addOnOffsetChangedListener(appBarStateChangeListener)

        binding.searchingCalendarCalendar.apply{
            showDayOfWeekTitle(false)
            //setRangeDate(startDate.time, endDate.time)
            //setSelectionDate(startDate.time, endDate.time)
        }

        // 날짜 범위 선택 -> 위에 띄운다
        binding.searchingCalendarCalendar.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
            Log.d("로그","날짜 범위 선택: Date: $startDate ~ $endDate, label: $startLabel , $endLabel")
            val startDateList = startLabel.split(' ').let{
                mStartDate = it[0]
                mEndMonth = it[1].replace("월","")
                mStartYear = it[2]

                Log.d("로그", "parsed dates: startDate: $mStartDate , startMonth: $mStartMonth ," +
                        " startYear: $mStartYear")
            }

            val endDateList = endLabel.split(' ').let{
                mEndDate = it[0]
                mEndMonth = it[1].replace("월","")
                mEndYear = it[2]

                Log.d("로그", "parsed dates: endDate: $mEndDate , endMonth: $mEndMonth ," +
                        " endYear: $mEndYear")
            }

            // 연도가 같을때, 다를 때를 구분한다
            when(mStartYear == mEndYear){
                true -> binding.searchingCalendarTextRange.text = String.format("%s월 %s일 ~ %s월" +
                        " %s일", mStartMonth,mStartDate,mEndMonth, mEndDate)
                false -> binding.searchingCalendarTextRange.text = String.format("%s년 %s월 %s일 ~" +
                        " %s년 %s월 %s일", mStartYear,mStartMonth,mStartDate,mEndYear,mEndMonth,mEndDate)
            }

        }

        // 날짜 선택 -> 상단에 띄운다
        binding.searchingCalendarCalendar.setOnStartSelectedListener { startDate, label ->
            Log.d("로그","날짜 하나 선택: startDate: $startDate, label: $label")

            val dateList = label.split(' ').let{
                mStartDate = it[0]
                mStartMonth = it[1].replace("월", "")
                mStartYear = it[2]
                Log.d("로그", "parsed date: date: $mStartDate , month: $mStartMonth , year: $mStartYear")
                binding.searchingCalendarTextRange.text = String.format("%s월 %s일", mStartMonth,mStartDate)
            }

        }

        // 날짜 선택 -> 다음 단계
        binding.searchingCalendarNext.setOnClickListener(onClickNext)

    }

    // 커스텀 앱바 오프셋 체인지 리스너 ->
    // 스크롤 뷰가 맨 위에 오면 스크롤을 멈추고, 달력만 스크롤 한다
    private val appBarStateChangeListener: AppBarStateChangeListener =
        object: AppBarStateChangeListener(){
            override fun onStateChanged(appBarLayout: AppBarLayout, state: State) {
                when(state){
                    State.EXPANDED -> binding.searchingCalendarScroll.scrollable = true
                    State.COLLAPSED -> binding.searchingCalendarScroll.scrollable = false
                    State.IDLE -> binding.searchingCalendarScroll.scrollable = false
                }
            }
        }


    private val onClickNext = View.OnClickListener {

    }
}