package com.rp2.star.airbnb.src.main.search.searching.pay

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.databinding.FragmentSearchingPayBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView


class SearchingPayFragment(val searchingView: SearchingActivityView) :
    BaseFragment<FragmentSearchingPayBinding>(FragmentSearchingPayBinding::bind,
    R.layout.fragment_searching_pay), SearchingPayFragmentView{

    private val sp = ApplicationClass.sSharedPreferences
    private var adult: Int = -1
    private var kid: Int = 0
    private var child: Int = 0
    private var paymentId: Int = 5
    private var lodgeId: Int = -1
    private var headCountStr = ""
    private var message: String = ""
    private var startDate: String = ""
    private var endDate: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 게스트 정보, 예약할 날짜 반영
        sp.apply{
            adult = getInt("adult", 1)
            kid = getInt("kid", 0)
            child = getInt("child", 0)
            startDate = getString("startDate", "").toString()
            endDate = getString("endDate", "").toString()
        }

        binding.payBtnAddMethod.setOnClickListener(onAddMethod)

        binding.payMsgContents.addTextChangedListener(msgChangeListener)

        binding.payBtnGuest.setOnClickListener {
            searchingView.goToCompany2()
        }

        // 결제 버튼
        binding.payBtnPay.setOnClickListener {
            headCountStr = String.format("성인: $adult, 어린이: $kid, 유아: $child")
            lodgeId = sp.getInt("lodgeId", 3)
            paymentId = sp.getInt("paymentId", 5)

            showLoadingDialog(context!!)
            SearchingPayService(this).tryPostReserveLodge(startDate, endDate, headCountStr,
                message, paymentId, lodgeId)
        }
    }

    val onAddMethod = View.OnClickListener {
        searchingView.goToCard()
    }

    val msgChangeListener = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            message = p0.toString()
        }

        override fun afterTextChanged(p0: Editable?) {}

    }

    override fun onPostReserveSuccess(response: BaseResponse) {
        Log.d("로그", "onPostReserveSuccess() called - response: $response")
        dismissLoadingDialog()
        when(response.isSuccess){
            true -> {
                showCustomLongToast("여행이 예약되었습니다.")
            }
            false -> {
                showCustomLongToast("예약에 실패했습니다. 아래 내용을 확인해주세요.\n 원인: $message")
            }
        }
    }

    override fun onPostReserveFailure(message: String) {
        Log.d("로그", "onPostReserveFailure() called - message: $message")
        dismissLoadingDialog()
        showCustomToast("카드 등록 실패: 네트워크 확인 후 다시 시도해주세요")
    }
}