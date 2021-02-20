package com.rp2.star.airbnb.src.main.trip

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.src.main.trip.models.ScheduleResult
import kotlinx.android.synthetic.main.recycler_view_trip_item.view.*

class TripRecyclerAdapter(val context: Context, private var tripList: ArrayList<ScheduleResult>):
    RecyclerView.Adapter<TripRecyclerAdapter.TripViewHolder>(){

    // 뷰홀더를 생성해서 반환한다
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripRecyclerAdapter.TripViewHolder {
        val viewHolder =TripViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_show_lodge, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: TripRecyclerAdapter.TripViewHolder,
        position: Int
    ) {
        holder.setValue(tripList[position])
    }

    override fun getItemCount() =tripList.size

    // custom ViewHolder 클래스 정의
    inner class TripViewHolder(tripView: View) : RecyclerView.ViewHolder(tripView){
        private val view: View = tripView.trip_item_root
        private val imgL: ImageView = tripView.trip_item_img_l
        private val dates: TextView = tripView.trip_item_dates
        private val title: TextView = tripView.trip_item_title
        private val imgS: ImageView = tripView.trip_item_img_s
        private val captial: TextView = tripView.trip_item_captial
        private val moreBtn1: LinearLayout? = tripView.trip_item_btn_more1
        private val moreBtn2: Button = tripView.trip_item_btn_more2


        fun setValue(scheduleItem: ScheduleResult) {
            view.clipToOutline = true

            // 숙소 대표 사진 -> 리사이클러 뷰 안에 뷰페이저를 넣는다
            imgL.clipToOutline = true
            val imgUrl = scheduleItem.lodgingImg
            Glide.with(context!!)
                .load(imgUrl)
                .error(R.drawable.search_main_seoul)
                .into(imgL)

            // 날짜
            dates.text = String.format("${scheduleItem.startDates} - ${scheduleItem.endDates}")

            // 지역
            captial.text = String.format("${scheduleItem.lodgingCapital}")

            // 작은 이미지
            imgS.clipToOutline = true
            Glide.with(context!!)
                .load(imgUrl)
                .error(R.drawable.search_main_seoul)
                .into(imgS)

            title.text = scheduleItem.lodgingName
        }
    }

/*
    // 숙소 리스트를 받아온다, 프래그먼트에서 실행
    fun provideList(tripList: ArrayList<ScheduleResult>){
        this.tripList = tripList
        this.notifyDataSetChanged()
    }
 */
}