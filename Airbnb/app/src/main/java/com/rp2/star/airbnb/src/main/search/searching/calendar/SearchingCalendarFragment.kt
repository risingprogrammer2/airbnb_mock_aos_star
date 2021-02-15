package com.rp2.star.airbnb.src.main.search.searching.calendar

import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.appbar.AppBarLayout
import com.google.gson.Gson
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingCalendarBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.DatesData


class SearchingCalendarFragment(private val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingCalendarBinding>(
        FragmentSearchingCalendarBinding::bind, R.layout.fragment_searching_calendar
    ){

    private val sp = ApplicationClass.sSharedPreferences
    private var mStartDate: String? = null
    private var mEndDate: String? = null
    private var mStartMonth: String? = null
    private var mEndMonth: String? = null
    private var mStartYear: String? = null
    private var mEndYear: String? = null
    private var mDatesString: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchingCalendarTextQuery.text = sp.getString("query", null)

        // 위에 모서리 둥글게
        binding.searchingCalendarLayoutTop.clipToOutline = true

        binding.searchingCalendarAppbar.addOnOffsetChangedListener(appBarStateChangeListener)

        binding.searchingCalendarCalendar.apply{
            showDayOfWeekTitle(false)
            //setRangeDate(startDate.time, endDate.time)
            //setSelectionDate(startDate.time, endDate.time)
        }

        // 날짜 범위 선택 -> 위에 띄운다
        binding.searchingCalendarCalendar.setOnRangeSelectedListener { startDate, endDate, startLabel, endLabel ->
            Log.d("로그", "날짜 범위 선택: Date: $startDate ~ $endDate, label: $startLabel , $endLabel")
            val startDateList = startLabel.split(' ').let{
                mStartDate = it[0]
                mEndMonth = it[1].replace("월", "")
                mStartYear = it[2]

                Log.d(
                    "로그", "parsed dates: startDate: $mStartDate , startMonth: $mStartMonth ," +
                            " startYear: $mStartYear"
                )
            }

            val endDateList = endLabel.split(' ').let{
                mEndDate = it[0]
                mEndMonth = it[1].replace("월", "")
                mEndYear = it[2]

                Log.d(
                    "로그", "parsed dates: endDate: $mEndDate , endMonth: $mEndMonth ," +
                            " endYear: $mEndYear"
                )
            }

            // 연도가 같을때, 다를 때를 구분한다
            when(mStartYear == mEndYear){
                true -> mDatesString = String.format(
                    "%s월 %s일 ~ %s월 %s일",
                    mStartMonth, mStartDate, mEndMonth, mEndDate
                )

                false -> mDatesString = String.format(
                    "%s년 %s월 %s일 ~ %s년 %s월 %s일",
                    mStartYear, mStartMonth, mStartDate, mEndYear, mEndMonth, mEndDate
                )
            }

            // 다음 버튼 활성화
            binding.searchingCalendarNext.isEnabled = true

            // 상단에 선택 날짜를 띄운다
            binding.searchingCalendarTextRange.text = mDatesString

        }

        // 날짜 선택 -> 상단에 띄운다
        binding.searchingCalendarCalendar.setOnStartSelectedListener { startDate, label ->
            Log.d("로그", "날짜 하나 선택: startDate: $startDate, label: $label")

            // 다음 버튼 활성화
            binding.searchingCalendarNext.isEnabled = false

            val dateList = label.split(' ').let{
                mStartDate = it[0]
                mStartMonth = it[1].replace("월", "")
                mStartYear = it[2]
                mEndDate = null
                mEndMonth = null
                mEndYear = null

                Log.d(
                    "로그",
                    "parsed date: date: $mStartDate , month: $mStartMonth , year: $mStartYear"
                )
                mDatesString = String.format("%s월 %s일", mStartMonth, mStartDate)
                binding.searchingCalendarTextRange.text = mDatesString
            }

        }

        // 날짜 선택 -> 다음 단계
        binding.searchingCalendarNext.setOnClickListener(onClickNext)

        binding.searchingCalendarBack.setOnClickListener {
            searchingView.goToStep2()
        }

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


    // 일행 선택으로 이동
    private val onClickNext = View.OnClickListener {
        val spEditor = sp.edit()
        val gson = Gson()
        val datesData =
            DatesData(
                mStartDate!!,
                mStartMonth!!,
                mStartYear,
                mEndDate,
                mEndMonth,
                mEndYear
            )
        val datesDataJson = gson.toJson(datesData)
        Log.d("로그", "SearchingCalendar - onClickNext() called, datesData: $datesDataJson")
        spEditor.putString("datesDataJson", datesDataJson)
        spEditor.putString("datesString", mDatesString)
        spEditor.apply()

        searchingView.goToCompany()

/*
        // 객체로 다시 불러오는 방법
        val personString: String = sharedPreferences.getString(sfPersonKey, "실패")
        val loadedPerson: Person = gson.fromJson(personJson, Person::class.java) //펄슨 객체로 부르기
*/

    }
}