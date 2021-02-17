import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.gson.Gson
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingDetailCalendarBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.detail_calendar.SearchingDetailCalendarFragmentView
import com.rp2.star.airbnb.src.main.search.searching.detail_calendar.SearchingDetailCalendarService
import com.rp2.star.airbnb.src.main.search.searching.models.DatesData
import com.rp2.star.airbnb.src.main.search.searching.models.GetImpossibleDatesResponse
import com.savvi.rangedatepicker.CalendarPickerView
import java.text.SimpleDateFormat
import java.util.*


class SearchingDetailCalendarFragment(private val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingDetailCalendarBinding>(
        FragmentSearchingDetailCalendarBinding::bind, R.layout.fragment_searching_detail_calendar
    ), SearchingDetailCalendarFragmentView {

    private val sp = ApplicationClass.sSharedPreferences
    private var mStartDate: String? = null
    private var mEndDate: String? = null
    private var mStartMonth: String? = null
    private var mEndMonth: String? = null
    private var mStartYear: String? = null
    private var mEndYear: String? = null
    private var mDatesString: String? = null
    private lateinit var noByHostList: MutableList<Date>
    private lateinit var noByGuestsList: MutableList<Date>
    private lateinit var totalNoList: MutableList<Date>
    private val MS_PER_DAY: Long = 86400000
    private var lodgeId = 2
    private var isRanged = false
    private var startDate: String? = null
    private var endDate: String? = null
    private lateinit var dateFormatter: SimpleDateFormat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //binding.searchingCalendarTextQuery.text = sp.getString("query", null)

        // Date 변환
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        startDate = dateFormatter.format(Date())

        /*val strDate = "2016-10-17 18:30"
        val date: Date? = dateFormatter.parse(strDate)*/

        // lodgeId = arguments!!.getInt("lodgeId", 2)

        noByGuestsList = ArrayList()
        noByHostList = mutableListOf<Date>()
        totalNoList = ArrayList()

        val start = Calendar.getInstance()
        val end = Calendar.getInstance()
        end.add(Calendar.YEAR, 1)

        // 달력 초기화
        binding.detailCalendarPicker.init(start.time, end.time) //
            .inMode(CalendarPickerView.SelectionMode.RANGE)
            .withSelectedDate(Date())
//            .withDeactivateDates(arrayListOf<Int>(1,2,3))
// deactivates given dates, non selectable
//            .withDeactivateDates(abc)
// highlight dates in red color, mean they are aleady used.
//            .withHighlightedDates(arrayList)
// add subtitles to the dates pass a arrayList of SubTitle objects with date and text
//            .withSubtitles(getSubtitle())

        // 예약 불가능한 날짜를 받아온다.
        showLoadingDialog(context!!)
        SearchingDetailCalendarService(this).tryGetImpossibleDates(lodgeId)

        // 뒤로가기 버튼
        binding.detailCalendarBtnBack.setOnClickListener {
            //searchingView.goToDetail(lodgeId)
        }

        // 날짜 선택 시 동작 설정
        binding.detailCalendarPicker.setOnDateSelectedListener(object: CalendarPickerView.OnDateSelectedListener{
            override fun onDateSelected(date: Date?) {
                Log.d("로그","onDateSelected() called - date: $date")
                Log.d("로그", "onDateSelected() called - isRanged: $isRanged")

                when(isRanged){
                    true ->{
                        startDate = dateFormatter.format(date)
                        endDate = null
                        binding.detailCalendarSave.isEnabled = false
                        isRanged = !isRanged
                    }
                    false ->{
                        // 삭제버튼 후 startDate가 비어있으면 클릭한 값을 넣어준다
                        if(startDate == null) {
                            startDate = dateFormatter.format(date)
                        }else{
                            val tempStart = dateFormatter.parse(startDate!!)
                            if(date!!.time > tempStart!!.time){
                                endDate = dateFormatter.format(date)
                                binding.detailCalendarSave.isEnabled = true
                                isRanged = !isRanged
                            }else{
                                startDate = dateFormatter.format(date)
                            }
                        }
                    }
                }

            }

            override fun onDateUnselected(date: Date?) {
                Log.d("로그","onDateUnselected() called - date: $date")
            }
        })

        // 이미 선택된 날짜랑 같은 날짜는 클릭 불가능하게 한다
        binding.detailCalendarPicker.setDateSelectableFilter { date ->
            val dateStr = dateFormatter.format(date)
            !(dateStr == startDate || dateStr == endDate)
        }

        // 저장 클릭 -> 쉐어드저장소에 날짜를 저장하고 나간다.
       binding.detailCalendarSave.setOnClickListener{
           val spEditor = sp.edit()
           spEditor.putString("startDate", startDate)
           spEditor.putString("endDate", endDate)
           spEditor.apply()
           searchingView.goToDetail(lodgeId)
        }

        binding.detailCalendarBtnErase.setOnClickListener {
            binding.detailCalendarPicker.clearSelectedDates()
            startDate = null
            endDate = null
            isRanged = false
            binding.detailCalendarSave.isEnabled = false
        }




        /*// 날짜 범위 선택 -> 위에 띄운다
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
        binding.searchingCalendarPicker.setOnStartSelectedListener { startDate, label ->
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

        }*/
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
        spEditor.commit()

        searchingView.goToCompany()

/*
        // 객체로 다시 불러오는 방법
        val personString: String = sharedPreferences.getString(sfPersonKey, "실패")
        val loadedPerson: Person = gson.fromJson(personJson, Person::class.java) //펄슨 객체로 부르기
*/

    }



    // 예약 불가능한 날짜 요청 통신 성공
    override fun onGetImpossibleDatesSuccess(response: GetImpossibleDatesResponse) {
        Log.d("로그", "onGetImpossibleDatesSuccess() called - response: $response")
        dismissLoadingDialog()

        when(response.isSuccess){
            // 요청 성공하면 각 날짜를 Date 리스트에 모두 담는다
            true -> {

                val noDatesByHost = response.noDatesByHost
                val noDatesByGuests = response.noDatesByGuests
                val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())


                // 호스트가 지정한 예약 불가능 날짜
                if(noDatesByHost.size > 0){
                    for(i in 0 until noDatesByHost.size){
                        var startStr = noDatesByHost[i].startDates  // 문자열: yyyy-MM-dd
                        val endStr = noDatesByHost[i].endDates
                        Log.d("로그","startStr: $startStr, endStr: $endStr")
                        var startDate = dateFormat.parse(startStr)  // Date 클래스
                        val endDate = dateFormat.parse(endStr)
                        Log.d("로그","startDate: $startDate, endDate: $endDate")
                        var startMS = startDate.time                // 밀리세컨드
                        val endMS = endDate.time
                        Log.d("로그","startMS: $startMS, endMS: $endMS")
                        lateinit var addedDate: Date

                        // 시작날 ~ 끝날 까지 하루마다 비교해서
                        // Date 클래스로 변환해서 추가한다
                        while(startMS <= endMS){
                            startStr = dateFormat.format(startMS)    // 밀리세컨드 -> 문자열로 다시 변환
                            startDate = dateFormat.parse(startStr)  // Date 클래스로 변환
                            noByHostList.add(startDate)             // 비활성화 Date리스트에 추가
                            startMS += MS_PER_DAY                   // 그 다음날로 변경
                        }
                        totalNoList.addAll(noByHostList)
                    }
                }

                // 이미 예약돼서 불가능한 날짜
                if(noDatesByGuests.size > 0){
                    for(i in 0 until noDatesByGuests.size){
                        var startStr = noDatesByHost[i].startDates  // 문자열: yyyy-MM-dd
                        val endStr = noDatesByHost[i].endDates
                        var startDate = dateFormat.parse(startStr)  // Date 클래스
                        val endDate = dateFormat.parse(endStr)
                        var startMS = startDate.time                // 밀리세컨드
                        val endMS = endDate.time

                        // 시작날 ~ 끝날 까지 하루마다 비교해서
                        // Date 클래스로 변환해서 추가한다
                        while(startMS <= endMS){
                            startStr = dateFormat.format(startMS)    // 밀리세컨드 -> 문자열로 다시 변환
                            startDate = dateFormat.parse(startStr)  // Date 클래스로 변환
                            noByGuestsList.add(startDate)             // 비활성화 Date리스트에 추가
                            startMS += MS_PER_DAY                   // 그 다음날로 변경
                        }
                    }
                    totalNoList.addAll(noByGuestsList)
                }
                binding.detailCalendarPicker.highlightDates(totalNoList)
            }
            false -> {
                showCustomToast("예약 가능 날짜를 확인할 수 없습니다, 잠시 후 다시 시도해주세요." +
                        "원인: $response.message")
            }
        }
    }

    // 예약 불가능한 날짜 요청 통신 성공
    override fun onGetImpossibleDatesFailure(message: String) {
        Log.d("로그", "onGetImpossibleDatesFailure() called - message: $message")
        dismissLoadingDialog()

        showCustomToast("예약 불가능 날짜 요청 통신 실패: message: $message")
    }
}