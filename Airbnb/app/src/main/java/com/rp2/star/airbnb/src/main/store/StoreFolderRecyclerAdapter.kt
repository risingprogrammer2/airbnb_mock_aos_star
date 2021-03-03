package com.rp2.star.airbnb.src.main.store

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
import kotlinx.android.synthetic.main.recycler_view_folder_item.view.*

class StoreFolderRecyclerAdapter(val context: Context,
                                 private var folderList: ArrayList<Saves>):
    RecyclerView.Adapter<StoreFolderRecyclerAdapter.FolderHolder>() {


    // 뷰홀더를 생성해서 반환한다
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreFolderRecyclerAdapter.FolderHolder {
        val viewHolder = FolderHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_folder_item, parent, false)
        )
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: StoreFolderRecyclerAdapter.FolderHolder,
        position: Int
    ) {
        holder.setValue(folderList[position])
    }

    override fun getItemCount() = folderList.size

    inner class FolderHolder(folderView: View) : RecyclerView.ViewHolder(folderView) {
        private val root: View = folderView.folder_item_root        // 아이템 루트 레이아웃
        private val img1: ImageView = folderView.folder_item_img1   // 큰사진
        private val img2: ImageView = folderView.folder_item_img2
        private val img3: ImageView = folderView.folder_item_img3
        private val dates: TextView = folderView.folder_item_dates  // 항상 or 날짜 범위
        private val title: TextView = folderView.folder_item_title
        private val numLodge: TextView = folderView.folder_item_num_lodge   // 숙소 n개

        // 데이터 타입 수정해야함
        fun setValue(folder: Saves) {
            root.clipToOutline = true
            img1.clipToOutline = true
            img2.clipToOutline = true
            img3.clipToOutline = true

            // 대표 이미지 3개 적용
            val imgList = folder.imageList
            when (imgList.size) {
                1 -> {
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img1)
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img2)
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img3)
                }
                2 -> {
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img1)
                    Glide.with(context!!)
                        .load(imgList[1])
                        .error(R.drawable.search_main_seoul)
                        .into(img2)
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img3)
                }
                else -> {
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img1)
                    Glide.with(context!!)
                        .load(imgList[0])
                        .error(R.drawable.search_main_seoul)
                        .into(img2)
                    Glide.with(context!!)
                        .load(imgList[2])
                        .error(R.drawable.search_main_seoul)
                        .into(img3)
                }
            }

            // 날짜
            dates.setText("항상")

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