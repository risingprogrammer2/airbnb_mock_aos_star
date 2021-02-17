package com.rp2.star.airbnb.src.main.search.searching.review

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.src.main.search.searching.models.ResultLodgeDetailLodgeReview
import kotlinx.android.synthetic.main.searching_review_recycler_item.view.*

class SearchingReviewRecyclerAdapter(val context: Context,
                                     private val reviewList: ArrayList<ResultLodgeDetailLodgeReview>
): RecyclerView.Adapter<SearchingReviewRecyclerAdapter.ReviewHolder>(){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchingReviewRecyclerAdapter.ReviewHolder {
        val viewHolder = ReviewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.searching_review_recycler_item, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: SearchingReviewRecyclerAdapter.ReviewHolder,
        position: Int
    ) {
        TODO("Not yet implemented")
    }

    override fun getItemCount() = reviewList.size

    inner class ReviewHolder(reviewView: View): RecyclerView.ViewHolder(reviewView) {
        private val img: ImageView = reviewView.searching_review_recycler_img
        private val name: TextView = reviewView.searching_review_recycler_name
        private val dates: TextView = reviewView.searching_review_recycler_dates
        private val contents: TextView = reviewView.searching_review_recycler_contents


        fun setValue(reviewData: ResultLodgeDetailLodgeReview) {
            // 프로필 사진
            img.clipToOutline = true
            Glide.with(context)
                .load(reviewData.img)
                .into(img)

            // 이름
            name.text = reviewData.firstName
            // 날짜
            dates.text = reviewData.createDate
            // 리뷰 내용
            contents.text = reviewData.comment

        }
    }

}