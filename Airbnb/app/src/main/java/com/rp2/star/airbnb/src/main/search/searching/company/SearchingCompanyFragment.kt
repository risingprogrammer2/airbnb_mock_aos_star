package com.rp2.star.airbnb.src.main.search.searching.company

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingCompanyBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView


class SearchingCompanyFragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingCompanyBinding>(FragmentSearchingCompanyBinding::bind,
    R.layout.fragment_searching_company){

    private val sp = ApplicationClass.sSharedPreferences
    private var adult: Int = 0
    private var kid: Int = 0
    private var child: Int = 0
    private var total: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchingCompanyTextQuery.text = sp.getString("query","null")
        binding.searchingCompanyTextRange.text = sp.getString("datesString", "null")

        // 다음 버튼
        binding.searchingCompanyNext.setOnClickListener{
            sp.edit().apply{
                putInt("adult", adult)
                putInt("kid", kid)
                putInt("child", child)
            }
            searchingView.goToShow()
        }

        // 건너뛰기 버튼
        binding.searchingCompanySkip.setOnClickListener {
            searchingView.goToShow()
        }

        // 뒤로가기 버튼
        binding.searchingCompanyBack.setOnClickListener {
            searchingView.goToCalendar()
        }


        // 인원 버튼 리스너 등록
        for(i in 0..2){
            val idx = i+1
            val minusId = resources.getIdentifier("searching_company_btn${idx}_minus",
                "id", activity!!.packageName)
            val numId = resources.getIdentifier("searching_company_btn${idx}_number",
                "id", activity!!.packageName)
            val plusId = resources.getIdentifier("searching_company_btn${idx}_plus",
                "id", activity!!.packageName)
            Log.d("로그","minusId: $minusId , numId: $numId , plusId: $plusId")

            val minusBtn: TextView = binding.root.findViewById(minusId)
            val numText: TextView = binding.root.findViewById(numId)
            val plusBtn: TextView = binding.root.findViewById(plusId)

            // 마이너스 버튼
            minusBtn.setOnClickListener {
                when(idx) {
                    0 -> {
                        if(adult > 0){
                            adult -= 1
                            total -= 1
                            numText.text = String.format("%d", adult)
                        }
                    }
                    1 -> {
                        if(kid > 0){
                            kid -= 1
                            total -=1
                            numText.text = String.format("%d", kid)
                        }
                    }
                    2 -> {
                        if(child > 0){
                            child -= 1
                            total -=1
                            numText.text = String.format("%d", child)
                        }
                    }
                }

            }

            // 플러스버튼
            plusBtn.setOnClickListener {
                when(idx) {
                    0 -> {
                        adult += 1
                        total +=1
                        numText.text = String.format("%d", adult)
                    }
                    1 -> {
                        kid += 1
                        total +=1
                        numText.text = String.format("%d", kid)
                    }
                    2 -> {
                        child += 1
                        total +=1
                        numText.text = String.format("%d", child)
                    }
                }
            }

        }
    }
}