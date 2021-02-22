package com.rp2.star.airbnb.src.main.store

import android.os.Bundle
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainStoreBinding
import com.rp2.star.airbnb.src.main.store.in_folder.models.GetFoldersResponse


class StoreFragment : BaseFragment<FragmentMainStoreBinding>(FragmentMainStoreBinding::bind,
    R.layout.fragment_main_store), StoreFragmentView{

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onGetFoldersSuccess(response: GetFoldersResponse) {
        Log.d("로그", "onGetFoldersSuccess() called - response: $response")
        dismissLoadingDialog()

        when(response.isSuccess){
            true -> {Log.d("로그", "isSuccess: true - message: ${response.message}")}

            false -> {
                showCustomLongToast("폴더를 불러오지 못했습니다, 다음을 확인해주세요.\n" +
                        "${response.message}")
            }
        }
    }

    override fun onGetFoldersFailure(message: String) {
        Log.d("로그", "onGetFoldersFailure() called - message: $message")
        dismissLoadingDialog()

        showCustomToast("네트워크 확인 후 다시 시도해주세요.\nmessage: $message")
    }
}