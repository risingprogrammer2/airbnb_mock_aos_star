package com.rp2.star.airbnb.src.main.search.searching.show

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.src.main.search.searching.models.ResultLodgeByCity
import kotlinx.android.synthetic.main.recycler_view_show_lodge.view.*
import me.relex.circleindicator.CircleIndicator3

class SearchingShowLodgeAdapter(val context: Context, val fragment: SearchingShowFragment,
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
        private val viewPager: ViewPager2 = lodgeView.show_recycler_view_viewPager
        private val superhost: TextView = lodgeView.recycler_show_lodge_superhost
        private val isStoredImg: ImageView = lodgeView.recycler_show_lodge_store
        private val rating: TextView = lodgeView.recycler_show_lodge_rating
        private val location: TextView = lodgeView.recycler_show_lodge_location
        private val lodgeTitle: TextView = lodgeView.recycler_show_lodge_title
        private val indicator: CircleIndicator3 = lodgeView.recycler_show_lodge_indicator




        fun setValue(resultLodgeByCity: ResultLodgeByCity){
            // 숙소 대표 사진 -> 리사이클러 뷰 안에 뷰페이저를 넣는다
            val imgUrlList = resultLodgeByCity.lodgeImages
            val searchingShowSliderAdapter = SearchingShowSliderAdapter(fragment,imgUrlList)
            viewPager.adapter = searchingShowSliderAdapter

            // 인디게이터 설정
            indicator.setViewPager(viewPager)
            indicator.createIndicators(imgUrlList.size,0)

            // 슈퍼호스트 표시
            if(resultLodgeByCity.superhost == "Y"){
                superhost.visibility = View.VISIBLE
            }else{
                superhost.visibility = View.GONE
            }

            // 찜해놨으면 찜표시로 바꾸고, 쉐어드저장소에 저장 -> 상세 클릭에서 활용
            val isStored: Boolean = resultLodgeByCity.isSave
            if(isStored){
                ApplicationClass.sSharedPreferences.edit()
                    .putBoolean("isStored", isStored).commit()
                isStoredImg.apply{
                    when(isStored){
                        true -> setImageResource(R.drawable.searching_detail_heart_filled)
                        false -> setImageResource(R.drawable.searching_detail_heart_blank)
                    }
                }
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
                Log.d("로그","숙소 상세 조회 리사이클러 뷰 클릭 - position: $pos")
                /*if(pos != RecyclerView.NOT_FOCUSABLE){
                    val lodgeId = lodgeList[pos].id
                    searchingActivityView.goToDetail(lodgeId)
                }*/
                val lodgeId = lodgeList[pos].id

                searchingActivityView.goToDetail(lodgeId)

            }

            // 숙소 찜하기 / 취소
            isStoredImg.setOnClickListener {
                val pos = adapterPosition
                val lodgeId = lodgeList[pos].id

                when(isStored){
                    false -> {
                        fragment.showLoadingDialog(fragment.context!!)
                        SearchingShowService(fragment).tryPostStoreLodge(
                            "하드코딩 폴더", lodgeId, pos)
                    }
                    true -> {
                        fragment.showLoadingDialog(fragment.context!!)
                        SearchingShowService(fragment).tryDeleteLodgeStore(lodgeId, pos)
                    }

                }
            }
        }

    }

    // 숙소 리스트를 받아온다, 프래그먼트에서 실행
    fun provideList(lodgeByCityList: ArrayList<ResultLodgeByCity>){
        this.lodgeList = lodgeByCityList
        this.notifyDataSetChanged()
    }

    fun changeHeartState(pos: Int){
        lodgeList[pos].isSave = !(lodgeList[pos].isSave)
    }
}