package com.rp2.star.airbnb.src.main.search.searching.card

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.databinding.FragmentSearchingCardBinding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.util.LoadingDialog


class SearchingCardFragment(val searchingView: SearchingActivityView):
    BottomSheetDialogFragment(), SearchingCardFragmentView {
    private var _binding: FragmentSearchingCardBinding? = null
    private val binding get() = _binding!!
    lateinit var mLoadingDialog: LoadingDialog

    private val sp = ApplicationClass.sSharedPreferences
    private var userId: Int = -1
    lateinit private var creditNumber: String
    lateinit private var creditPeriod: String
    lateinit private var creditCVV: String
    private var postalCode: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userId = sp.getInt("id", -1)



        // 비자 카드번호
        val cardNumChangedListener = MaskedTextChangedListener("[0000] [0000] [0000] [0000]",
            true, binding.cardNumInput, null, object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    Log.d(
                        "로그",
                        "extractedValue: $extractedValue, formattedValue: $formattedValue"
                    )
                    creditNumber = formattedValue
                }
            }
        )
        binding.cardNumInput.addTextChangedListener(cardNumChangedListener)
        binding.cardNumLayout.onFocusChangeListener = cardNumChangedListener

        // 카드 유효기간 - 00 / 00
        val periodListener = MaskedTextChangedListener("[00]{/}[00]",
            true, binding.cardDuringInput, null, object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    Log.d(
                        "로그",
                        "extractedValue: $extractedValue, formattedValue: $formattedValue"
                    )
                    creditPeriod = formattedValue
                }
            }
        )
        binding.cardDuringInput.addTextChangedListener(periodListener)
        binding.cardDuringLayout.onFocusChangeListener = periodListener

        // cvv 번호 - 3자리 숫자
        val cvvListener = MaskedTextChangedListener("[000]",
            true, binding.cardCVVInput, null, object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    Log.d(
                        "로그",
                        "extractedValue: $extractedValue, formattedValue: $formattedValue"
                    )
                    creditCVV = formattedValue
                }
            }
        )
        binding.cardCVVInput.addTextChangedListener(cvvListener)
        binding.cardCVVLayout.onFocusChangeListener = cvvListener

        // 우편번호 -> 5자리 숫자
        val postalListener = MaskedTextChangedListener("[00000]",
            true, binding.cardPostalInput, null, object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    Log.d(
                        "로그",
                        "extractedValue: $extractedValue, formattedValue: $formattedValue"
                    )
                    postalCode = formattedValue
                }
            }
        )
        binding.cardPostalInput.addTextChangedListener(postalListener)
        binding.cardPostalLayout.onFocusChangeListener = postalListener

        // 완료 -> 등록 요청
        binding.cardBtnConfirm.setOnClickListener {
            showLoadingDialog(activity!!)
            creditNumber = creditNumber.replace(" ", "")
            creditPeriod = creditPeriod.replace("/", "")
            SearchingCardService(this).tryPostCardInfo(userId, creditNumber,
                creditPeriod, creditCVV, postalCode)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchingCardBinding.inflate(inflater, container, false)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CardTheme)
        return binding.root
    }


    val onClickBack = View.OnClickListener {
        searchingView.goToPay()
    }

    // 카드 등록 요청 통신 성공
    override fun onPostCardInfoSuccess(response: BaseResponse) {
        Log.d("로그", "onPostCardInfoSuccess() called - response: $response")
        dismissLoadingDialog()

        when(response.isSuccess){
            true -> {
                searchingView.goToPay()
            }
            false -> {
                showCustomLongToast("카드를 등록하지 못했습니다, 다음 사항을 확인해주세요\n원인: " +
                        "${response.message}")
            }
        }
    }

    // 카드 등록 요청 통신 실패
    override fun onPostCardInfoFailure(message: String) {
        Log.d("로그", "onPostCardInfoFailure() called - message: $message")
        dismissLoadingDialog()
        showCustomToast("카드 등록 실패: 네트워크 확인 후 다시 시도해주세요")
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showCustomLongToast(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

}