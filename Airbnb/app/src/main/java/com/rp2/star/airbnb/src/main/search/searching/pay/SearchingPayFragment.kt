package com.rp2.star.airbnb.src.main.search.searching.pay

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.databinding.FragmentSearchingPayBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import java.text.NumberFormat
import java.util.*


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
    private var price: String = ""
    private var totalPrice: Int = 0
    private var dayCnt: Int = 0
    private var serviceFee: Int = 0
    private var kwrTxt: String = ""
    private var imgUrl: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lodgeId = sp.getInt("lodgeId", 3)
        paymentId = sp.getInt("paymentId", 5)
        imgUrl = sp.getString("imgUrl", "").toString()

        kwrTxt = resources.getString(R.string.kwr)

        binding.payLodgeImg.clipToOutline = true


        Glide.with(context!!)
            .load(imgUrl)
            .error(R.drawable.my_page_me)
            .placeholder(R.drawable.my_page_me)
            .into(binding.payLodgeImg)

        // 게스트 정보, 예약할 날짜 반영
        sp.apply{
            adult = getInt("adult", 1)
            kid = getInt("kid", 0)
            child = getInt("child", 0)
            startDate = getString("startDate", "").toString()
            endDate = getString("endDate", "").toString()
        }

        // 요금 반영
        startDate = sp.getString("startDate", "").toString().replace("-", ".")
        endDate = sp.getString("endDate", "").toString().replace("-", ".")
        price = sp.getString("price", "").toString()
        dayCnt = sp.getInt("dayCnt", 0).toInt()
        totalPrice = price.replace(",", "").toInt()
        totalPrice *= dayCnt

        binding.payDatesTxt.text = String.format("${startDate}-${endDate}")

        binding.payLodgmentTxt.text = String.format("${kwrTxt}${price} x ${dayCnt}박")
        binding.payLodgmentFee.text = String.format("${kwrTxt}${NumberFormat.getInstance(
            Locale.getDefault())
            .format(totalPrice)}")

        serviceFee = (totalPrice.toFloat() / 14.2).toInt()
        binding.payServiceTaxFee.text = String.format("${kwrTxt}${NumberFormat.getInstance(
            Locale.getDefault())
            .format(serviceFee)}")
        binding.payServiceTaxFee.text = String.format("${kwrTxt}${NumberFormat.getInstance(
            Locale.getDefault())
            .format(serviceFee)}")

        val lodgmentTax = serviceFee / 10
        binding.payLodgmentTaxFee.text = String.format("${kwrTxt}${NumberFormat.getInstance(
            Locale.getDefault())
            .format(lodgmentTax)}$")

        val realTotal: Int = totalPrice + serviceFee + lodgmentTax
        binding.payTotalFee.text = String.format("${kwrTxt}${NumberFormat.getInstance(
            Locale.getDefault())
            .format(realTotal)}$")





        binding.payBtnAddMethod.setOnClickListener(onAddMethod)

        binding.payMsgContents.addTextChangedListener(msgChangeListener)

        binding.payDatesBtn.setOnClickListener {
            sp.edit().putInt("company2", 1).commit()        // 결제화면에서 온 것임을 알림
            searchingView.goToDetailCalendar(lodgeId)
        }

        binding.payBtnGuest.setOnClickListener{
        sp.edit().putInt("company2", 0).commit()        // 결제화면에서 온 것임을 알림
            searchingView.goToCompany2()
        }

        // 결제 버튼
        binding.payBtnPay.setOnClickListener {
            headCountStr = String.format("성인: $adult, 어린이: $kid, 유아: $child")

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
                searchingView.goToDetail(lodgeId)
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

    override fun changeGuests(total: Int) {
        binding.payGuestTxt.text = String.format("게스트 ${total}명")
    }
}