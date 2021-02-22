package com.rp2.star.airbnb.src.main.store

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.src.main.store.in_folder.models.Folder
import kotlinx.android.synthetic.main.recycler_view_folder_item.view.*

class StoreFolderRecyclerAdapter(val context: Context):
    RecyclerView.Adapter<StoreFolderRecyclerAdapter.FolderHolder>(){

    private var folderList = ArrayList<Folder>()

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

    inner class FolderHolder(folderView: View) : RecyclerView.ViewHolder(folderView){
        private val root: View = folderView.folder_item_root        // 아이템 루트 레이아웃
        private val img1: ImageView = folderView.folder_item_img1   // 큰사진
        private val img2: ImageView = folderView.folder_item_img2
        private val img3: ImageView = folderView.folder_item_img3
        private val dates: TextView = folderView.folder_item_dates  // 항상 or 날짜 범위
        private val title: TextView = folderView.folder_item_title
        private val numLodge: TextView = folderView.folder_item_num_lodge   // 숙소 n개

        // 데이터 타입 수정해야함
        fun setValue(folder: Folder){
            root.clipToOutline = true
        }
    }


    // 숙소 리스트를 받아온다, 프래그먼트에서 실행
    fun provideList(folderList: ArrayList<Folder>){
        this.folderList = folderList
        this.notifyDataSetChanged()
    }
}