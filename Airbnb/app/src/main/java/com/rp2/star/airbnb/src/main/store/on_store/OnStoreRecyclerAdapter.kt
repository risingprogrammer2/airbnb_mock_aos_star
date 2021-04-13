package com.rp2.star.airbnb.src.main.store.on_store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.src.main.store.models.Saves
import kotlinx.android.synthetic.main.recycler_view_on_store_item.view.*

class OnStoreRecyclerAdapter(val context: Context,
                             private var folderList: ArrayList<Saves>,
                             val onStoreView: OnStoreView):
    RecyclerView.Adapter<OnStoreRecyclerAdapter.FolderHolder>() {


    // 뷰홀더를 생성해서 반환한다
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OnStoreRecyclerAdapter.FolderHolder {
        val viewHolder = FolderHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_on_store_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: OnStoreRecyclerAdapter.FolderHolder,
        position: Int
    ) {
        holder.setValue(folderList[position])
    }

    override fun getItemCount() = folderList.size

    inner class FolderHolder(folderView: View) : RecyclerView.ViewHolder(folderView) {
        private val img: ImageView = folderView.on_store_item_img   // 큰사진
        private val dates: TextView = folderView.on_store_item_dates  // 날짜 미정 or 날짜 범위
        private val title: TextView = folderView.on_store_item_title
        private val numLodge: TextView = folderView.on_store_item_num_lodge   // 숙소 n개
        private val rootView: View = folderView.rootView

        // 데이터 타입 수정해야함
        fun setValue(folder: Saves) {
            img.clipToOutline = true

            rootView.setOnClickListener {
                onStoreView.storeLodge(title.text.toString())
            }

            // 이미지 1개 적용
            val imgList = folder.imageList
            when (imgList.size) {
                1 -> {
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img)
                }
                else -> {
                    Glide.with(context!!)
                        .load(R.drawable.search_main_seoul)
                        .into(img)
                }
            }

            // 날짜
            dates.setText("날짜 미정")

            // 폴더 이름
            title.text = String.format(folder.folderName)

            // 숙소 개수
            numLodge.text = String.format("숙소 ${folder.imageList.size}개")
        }
    }

    fun provideFolderList(folders: ArrayList<Saves>){
        folderList = folders
        notifyDataSetChanged()
    }
}