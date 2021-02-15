package com.rp2.star.airbnb.src.main.search.searching.step1

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingStep1Binding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView


class SearchingStep1Fragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingStep1Binding>(FragmentSearchingStep1Binding::bind,
    R.layout.fragment_searching_step1){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchingStep1SearchView.setOnQueryTextListener(onQueryListener)

        // 뒤로 화살표 버튼
        binding.searchingStep1Back.setOnClickListener {
            activity!!.onBackPressed()
        }
    }

    private val onQueryListener = object: SearchView.OnQueryTextListener{

        // 검색했을 때
        override fun onQueryTextSubmit(query: String?): Boolean {
            Log.d("로그", "onQueryTextSubmit called - query: $query")
            if(!query.isNullOrEmpty()) {
                val spEditor = ApplicationClass.sSharedPreferences.edit()
                spEditor.putString("query",query)
                spEditor.apply()
                searchingView.goToStep2()
            }
            return true
        }

        // 입력받는 중
        override fun onQueryTextChange(newText: String?): Boolean {
            return true
        }
    }
}