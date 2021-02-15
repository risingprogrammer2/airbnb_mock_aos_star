package com.rp2.star.airbnb.src.main.search.searching.show

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.ResultLodgeByCity
import kotlinx.android.synthetic.main.recycler_view_show_lodge.view.*

class SearchingShowLodgeAdapter(val context: Context,
                                val searchingActivityView: SearchingActivityView):
    RecyclerView.Adapter<SearchingShowLodgeAdapter.LodgeViewHolder>(){

    private var lodgeList = ArrayList<ResultLodgeByCity>()

    // 뷰홀더를 생성해서 반환한다
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SearchingShowLodgeAdapter.LodgeViewHolder {
        val viewHolder =LodgeViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_show_lodge, parent, false))
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: SearchingShowLodgeAdapter.LodgeViewHolder,
        position: Int
    ) {
        holder.setValue(lodgeList[position])
    }

    override fun getItemCount() =lodgeList.size

    // custom ViewHolder 클래스 정의
    inner class LodgeViewHolder(lodgeView: View) : RecyclerView.ViewHolder(lodgeView){
        private val view: View = lodgeView.recycler_show_lodge_root
        private val img: ImageView = lodgeView.recycler_show_lodge_images
        private val superhost: TextView = lodgeView.recycler_show_lodge_superhost
        private val store: ImageView = lodgeView.recycler_show_lodge_store
        private val rating: TextView = lodgeView.recycler_show_lodge_rating
        private val location: TextView = lodgeView.recycler_show_lodge_location
        private val lodgeTitle: TextView = lodgeView.recycler_show_lodge_title




        fun setValue(resultLodgeByCity: ResultLodgeByCity){
            // 숙소 대표 사진
            val imgUrl = resultLodgeByCity.lodgeImages[0]
            Glide.with(context)
                .load(imgUrl)
                .into(img)
            // 슈퍼호스트 표시
            if(resultLodgeByCity.superhost == "Y"){
                superhost.visibility = View.VISIBLE
            }else{
                superhost.visibility = View.GONE
            }



            // 평점 값이 null이면 NEW로 출력
            resultLodgeByCity.rating.apply{
                when(this){
                    null -> rating.text = String.format("NEW")
                    else -> rating.text = this.toString()
                }
            }

            location.text = String.format("${resultLodgeByCity.lodgingType} " +
                    "${resultLodgeByCity.placeType} • ${resultLodgeByCity.city}")
            lodgeTitle.text = resultLodgeByCity.name

            // 각 숙소를 클릭하면 상세 조회 화면으로 이동한다.
            view.setOnClickListener{
                val pos = adapterPosition
                if(pos != RecyclerView.NOT_FOCUSABLE){
                    val lodgeId = lodgeList[pos].id
                    searchingActivityView.goToDetail(lodgeId)
                }

            }

            /*
            // 찜, 리뷰개수, 이미지 슬라이드 추가해야함
            if()
            */

        }

    }

    // 베스트셀러 리스트를 받아온다, 프래그먼트에서 실행
    fun provideList(lodgeByCityList: ArrayList<ResultLodgeByCity>){
        this.lodgeList = lodgeByCityList
        this.notifyDataSetChanged()
    }


}