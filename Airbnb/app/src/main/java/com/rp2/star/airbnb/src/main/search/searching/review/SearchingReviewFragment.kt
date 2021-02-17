package com.rp2.star.airbnb.src.main.search.searching.company

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentSearchingReviewBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.ResultLodgeDetailLodgeReview
import com.rp2.star.airbnb.src.main.search.searching.review.SearchingReviewRecyclerAdapter


class SearchingReviewFragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingReviewBinding>(FragmentSearchingReviewBinding::bind,
    R.layout.fragment_searching_review){

    private val sp = ApplicationClass.sSharedPreferences
    private lateinit var reviewList: ArrayList<ResultLodgeDetailLodgeReview>
    private lateinit var ratingList: FloatArray

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 상세화면에서 받아온 리뷰목록, 평가목록
        arguments!!.apply{
            reviewList = getParcelableArrayList<ResultLodgeDetailLodgeReview>("reviewList")!!
            ratingList = getFloatArray("ratingList")!!
        }

        // 평점 숫자 반영
        for(i in 0..5){
            val ratingId = resources.getIdentifier("searching_review_rating$i",
                "id", activity!!.packageName)
            val ratingTxtView = activity!!.findViewById<TextView>(ratingId)
            ratingTxtView.text = ratingList[i].toString()
        }

        // 평점에 맞게 0~50 사이로 progress bar 반영
        binding.seachingReviewRating0Bar.progress =
            (binding.seachingReviewRating0.text.toString().toFloat()*10).toInt()
        binding.seachingReviewRating1Bar.progress =
            (binding.seachingReviewRating1.text.toString().toFloat()*10).toInt()
        binding.seachingReviewRating2Bar.progress =
            (binding.seachingReviewRating2.text.toString().toFloat()*10).toInt()
        binding.seachingReviewRating3Bar.progress =
            (binding.seachingReviewRating3.text.toString().toFloat()*10).toInt()
        binding.seachingReviewRating4Bar.progress =
            (binding.seachingReviewRating4.text.toString().toFloat()*10).toInt()
        binding.seachingReviewRating5Bar.progress =
            (binding.seachingReviewRating5.text.toString().toFloat()*10).toInt()


        val searchingReviewRecyclerAdapter = SearchingReviewRecyclerAdapter(context!!, reviewList)
        binding.searchingReviewRecyclerView.adapter = searchingReviewRecyclerAdapter
        binding.searchingReviewRecyclerView.layoutManager = LinearLayoutManager(activity,
            LinearLayoutManager.VERTICAL, false)
    }
}


