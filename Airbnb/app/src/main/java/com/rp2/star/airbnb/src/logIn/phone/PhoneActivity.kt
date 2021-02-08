package com.rp2.star.airbnb.src.logIn.phone

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import androidx.core.text.isDigitsOnly
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.config.BaseResponse
import com.rp2.star.airbnb.databinding.ActivityPhoneBinding
import com.rp2.star.airbnb.src.logIn.models.PostCertNumCompRequest

class PhoneActivity : BaseActivity<ActivityPhoneBinding>(ActivityPhoneBinding::inflate),
    PhoneActivityView{

    private lateinit var mContext: Context
    private var inputNum = ""
    private lateinit var numViewArray: ArrayList<EditText>
    private lateinit var phoneNumber: String    // 서버에 전달할 전화번호: 국가번호 포함

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        // 하이픈 있는 번호
        val formattedNumber = intent.extras?.get("formatted") as String
        // 하이픈 없는 번호
        phoneNumber = intent.extras?.get("phone") as String
        val guideText = "문자 메시지를 통해 ${formattedNumber}번으로 보내드린 코드를 입력하세요."
        binding.phoneTextGuide1.text = guideText

        // 번호 입력 뷰들을 배열로 관리
        numViewArray = ArrayList()
        for(i in 0..3){
            val numViewId = resources.getIdentifier("phone_text_input_num$i","id",packageName)
            val numView: EditText = findViewById(numViewId)
            numViewArray.add(numView)

            // 포커스 되면 키보드가 올라온다
            numView.showSoftInputOnFocus = true
            // 숫자 입력시 동작 코드가 담긴 리스너
            numView.addTextChangedListener(numChangedListener)
        }



        // 서버에서 인증번호 전송 완료될 때까지 로딩화면을 보여준다
        showLoadingDialog(mContext)
        binding.root.visibility = View.GONE
        // 서버에 인증번호 전송 요청
        PhoneService(this).tryPostCertNum(phoneNumber)

//        Handler(Looper.getMainLooper()).postDelayed({
//            dismissLoadingDialog()
//            binding.root.visibility = View.VISIBLE
//        }, 1500)

    }

    // 서버로 인증번호 전송요청 통신 성공
    override fun onPostCertNumSuccess(response: BaseResponse){
        dismissLoadingDialog()
        binding.root.visibility = View.VISIBLE

        when(response.code){
            1000 -> showCustomToast("번호 전송 success: ${response.message}")
            else -> showCustomToast("번호 전송 failure: ${response.message}")
        }
    }

    // 서버로 인증번호 전송요청 통신 실패
    override fun onPostCertNumFailure(message: String) {
        dismissLoadingDialog()

        showCustomToast("네트워크 통신 오류: $message")
    }

    // 인증번호 비교 요청 통신 성공
    override fun onCompareCertNumSuccess(response: BaseResponse) {
        when(response.code){
            1000 -> showCustomToast("번호 비교 ${response.success}: ${response.message}")
            else -> showCustomToast("번호 비교 failure: ${response.message}")
        }
    }

    // 인증번호 비교 요청 통신 실패
    override fun onCompareCertNumFailure(message: String) {
        showCustomToast("네트워크 통신 오류: $message")
    }

    // 인증번호 입력: 백스페이스 입력 -> 포커스 이동 컨트롤
    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        Log.i("로그","view: $currentFocus , keyCode: $keyCode")

        val currentFocus = this.currentFocus

        when (keyCode){
            // 지우는 버튼을 누르면
            // 이전 뷰의 번호를 새로 쓰게 만든다
            KeyEvent.KEYCODE_DEL -> {

                // 백스페이스 -> 인증번호 리셋
                inputNum = ""

                when(currentFocus){
                    binding.phoneTextInputNum0 -> { }
                    binding.phoneTextInputNum1 -> {
                        binding.phoneTextInputNum0.text = null
                        binding.phoneTextInputNum0.isEnabled = true
                        binding.phoneTextInputNum0.requestFocus()
                        binding.phoneTextInputNum0.isCursorVisible = true

                        currentFocus.isEnabled = false

                    }
                    binding.phoneTextInputNum2 -> {
                        binding.phoneTextInputNum1.text = null
                        binding.phoneTextInputNum1.isEnabled = true
                        binding.phoneTextInputNum1.requestFocus()
                        binding.phoneTextInputNum1.isCursorVisible = true

                        currentFocus.isEnabled = false
                    }
                    binding.phoneTextInputNum3 -> {
                        binding.phoneTextInputNum2.text = null
                        binding.phoneTextInputNum2.isEnabled = true
                        binding.phoneTextInputNum2.requestFocus()
                        binding.phoneTextInputNum2.isCursorVisible = true

                        currentFocus.isEnabled = false
                    }
                }
            }
            else -> {
                super.onKeyUp(keyCode, event)
            }
        }
        return true
    }

    // 인증 번호 입력: 숫자 입력 -> 포커스 이동 컨트롤 리스너
    // 인증 번호 비교 요청 포함
    private val numChangedListener = object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val num = p0.toString()
            if(num.isDigitsOnly()){
                val currentView = currentFocus
                when(currentView?.id){

                    // 마지막 칸에 입력하면 서버에 자동으로 번호 비교를 요청한다
                    binding.phoneTextInputNum3.id -> {
                        for(i in 0..3){
                            inputNum += numViewArray[i].text.toString()
                        }

                        // 서버에 인증번호 비교 요청
                        val postCertNumCompRequest = PostCertNumCompRequest(phoneNumber, inputNum)
                        PhoneService(mContext as PhoneActivityView).tryPostCertNumCompare(postCertNumCompRequest)
                    }

                    // 나머지 칸에 숫자 입력 -> 자동 다음 뷰 이동, 이전 뷰 disabled
                    else -> {
                        val nextFocusId = currentView?.nextFocusForwardId
                        val nextFocus = findViewById<EditText>(nextFocusId!!)

                        nextFocus.isEnabled = true
                        nextFocus.requestFocus()
                        nextFocus.isCursorVisible = true

                        currentView.isEnabled = false
                    }
                }
            }
        }

        override fun afterTextChanged(p0: Editable?) {}

    }
}
