package com.rp2.star.airbnb.src.log_in

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.Log
import android.view.View
import com.kakao.sdk.auth.model.OAuthToken
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivityLogInBinding
import com.rp2.star.airbnb.src.log_in.input_profile.InputProfileActivity
import com.rp2.star.airbnb.src.log_in.models.NaverResponse
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import com.rp2.star.airbnb.src.log_in.phone.PhoneActivity
import com.rp2.star.airbnb.src.main.MainActivity
import java.util.regex.Pattern


class LogInActivity : BaseActivity<ActivityLogInBinding>(ActivityLogInBinding::inflate),
    LogInActivityView{

    private val OAUTH_CLIENT_ID = "MJqbP82f4Y5p5v2nLOGa"
    private val OAUTH_CLIENT_SECRET = "aEaReQ4RER"
    private val OAUTH_CLIENT_NAME = "AirbnbByStar"
    private val apiUrl = "https://openapi.naver.com/v1/nid/me"

    lateinit var mContext: Context
    private val naverModule = ApplicationClass.naverLoginModule


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this

        // 전화번호 입력할 때 형식 검사 -> 에러 메시지, 계속하기 버튼 활성화
        binding.logInTextInputNum.addTextChangedListener(numChangedListener)

        // 계속하기 버튼 클릭
        binding.logInBtnContinue.setOnClickListener(continueOnClickListener)
        binding.logInBtnContinue.isEnabled = false

        //카카오로 로그인 버튼 클릭
        binding.logInBtnKakaoLogIn.setOnClickListener(kakaoOnClickListener)

        //네이버로 로그인 버튼 클릭
        binding.logInBtnNaverLogIn.setOnClickListener(naverOnClickListener)

        // 종료 버튼
        binding.logInBtnClose.setOnClickListener{
            finish()
        }
    }

    // 전화번호 입력 editText 리스너: 휴대폰 번호, 집번호 유효성 검사, 하이픈 붙여서 출력
    private val numChangedListener = object :PhoneNumberFormattingTextWatcher(){
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            super.beforeTextChanged(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            super.onTextChanged(s, start, before, count)
            val formattedNumber = binding.logInTextInputNum.text.toString()
            Log.d("로그", "onTextChanged() called - $formattedNumber")

            when(this.isValidCellPhoneNumber(formattedNumber) ||
                    this.isValidHomePhoneNumber(formattedNumber)) {
                true -> {
                    // 전화번호 유효성 에러 표시 삭제
                    binding.logInLayoutInputNum.setBackgroundResource(R.drawable.log_in_text_phone_selector)

                    // 전화번호 유효 -> 버튼 활성화, 체크 이미지 출력, 에러 메세지 삭제
                    binding.logInTextInputNum.error = null
                    binding.logInBtnContinue.isEnabled = true
                    binding.logInImgCheck.visibility = View.VISIBLE
                }
                false -> {
                    // 전화번호 유효성 에러 표시
                    binding.logInLayoutInputNum.setBackgroundResource(R.drawable.log_in_text_phone_border_error)


                    // 전화번호 유효x - 버튼 비활성화, 체크 이미지 없애기, 에러 메세지 출력
                    binding.logInTextInputNum.error = " 유효하지 않은 전화번호"
                    binding.logInBtnContinue.isEnabled = false
                    binding.logInImgCheck.visibility = View.GONE
                }
            }
        }

        override fun afterTextChanged(s: Editable?) {
            super.afterTextChanged(s)
        }

        // 정규식으로 핸드폰번호 형식 검사
        fun isValidCellPhoneNumber(cellphoneNumber: String):Boolean {
            var returnValue = false
            var formattedNum = cellphoneNumber

            try
            {
                val regex = "^\\s*(010|011|016|017|018|019)(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$"
                val p = Pattern.compile(regex)
                val m = p.matcher(formattedNum)

                if (m.matches())
                {
                    returnValue = true
                }
                if ((returnValue && formattedNum != null
                            && formattedNum.length > 0
                            && formattedNum.startsWith("010")))
                {
                    formattedNum = formattedNum.replace("-", "")
                    if (formattedNum.length != 11)
                    {
                        returnValue = false
                    }
                }
                return returnValue
            }
            catch (e: Exception) {
                return false
            }
        }

        // 정규식으로 집 전화번호 형식 검사
        fun isValidHomePhoneNumber(phoneNumber: String): Boolean {
            var returnValue = false

            try {
                val regex =
                    "^\\s*(02|031|032|033|041|042|043|051|052|053|054|055|061|062|063|064|070)" +
                            "?(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$"
                val p = Pattern.compile(regex)
                val m = p.matcher(phoneNumber)
                if (m.matches()) {
                    returnValue = true
                }
                if(returnValue){
                    if(phoneNumber.split('-').size != 3){
                        returnValue = false
                    }
                }
                return returnValue
            }
            catch (e: Exception){
                return false
            }
        }
    }

    // 계속하기 버튼 클릭 리스너
    private val continueOnClickListener = View.OnClickListener {
        val phoneIntent = Intent(this, PhoneActivity::class.java)

        // 인증 요청한 번호
        val formattedNumber = binding.logInTextInputNum.text.toString()
        // 선택된 국가번호
        val selectedItem = binding.logInSpinnerNation.selectedItem.toString()
        val start = selectedItem.indexOf('(') + 2
        val end = selectedItem.lastIndex - 2
        val nationNumber = selectedItem.substring(start..end)

        // 하이픈 제거
        var phoneNumber = formattedNumber.replace("-", "")
        // 국가번호랑 합체: +8210xxxxoooo
        phoneNumber = nationNumber + phoneNumber.substring(1..phoneNumber.lastIndex)

        phoneIntent.putExtra("phone", phoneNumber)
        phoneIntent.putExtra("formatted", formattedNumber)
        startActivity(phoneIntent)

        // 화면 원래 상태로 되돌려놓음.
        binding.logInTextInputNum.setText("")
        binding.logInTextInputNum.error = null
        binding.logInBtnContinue.isEnabled = false
        binding.logInImgCheck.visibility = View.GONE
    }

    // 카카오로 로그인 버튼 클릭 리스너
    private val kakaoOnClickListener = View.OnClickListener {
        showLoadingDialog(this)

        LogInService(this).kakaoLogIn(this)
    }

    // 카카오 서버에 로그인 요청 성공 but 로그인 실패
    override fun onKakaoApiFailure(message: String) {
        Log.e("로그", "카카오 서버로부터 로그인 실패, error: $message")

        dismissLoadingDialog()
        showCustomLongToast("카카오 서버로부터 로그인 실패: $message")
    }

    // 카카오 서버에 로그인 요청, 로그인 성공
    override fun onKakaoApiSuccess(token: OAuthToken) {
        Log.d("로그", "카카오 서버로부터 로그인 성공, access token: ${token.accessToken}")

        LogInService(this).tryKakaoLogIn(token.accessToken)
    }

    // 팀 서버와 카카오 로그인 통신 성공했을 때,
    override fun onPostKakaoLogInSuccess(response: SignUpResponse) {
        Log.d("로그", "onPostKakaoLogInSuccess() called, response: $response")
        dismissLoadingDialog()


        val mainIntent = Intent(this, MainActivity::class.java)
        val inputProfileIntent = Intent(this, InputProfileActivity::class.java)

        when(response.message.toString()){
            "카카오 로그인 성공" -> {
                saveUserLogIn(response.result!!)
                LogInService(this).kakaoGetProfile()
                startActivity(mainIntent)
                finish()
            }
            "카카오 회원가입 성공" -> {
                saveUserLogIn(response.result!!)
                LogInService(this).kakaoGetProfile()
                startActivity(inputProfileIntent)
                finish()
            }
            else -> {
                Log.d("로그", "팀 서버로부터 카카오 로그인/회원가입 성공이라고 떴지만 오류발생")
            }
        }

    }

    // 팀 서버와 카카오 로그인 통신 실패
    override fun onPostKakaoLogInFailure(message: String) {
        Log.d("로그", "onPostKakaoLogInFailure() called, message: $message")

        dismissLoadingDialog()
        showCustomToast("네트워크 확인 후 다시 시도해주세요.")
    }

    // 네이버로 로그인 클릭 리스너
    private val naverOnClickListener = View.OnClickListener{
        showLoadingDialog(this)

        // 아래에 만든 핸들러를 인수로 넣어준다
        naverModule.startOauthLoginActivity(this, naverHandler)
    }

    // 네이버 서버로부터 로그인 reponse가 왔을 때 동작하는 핸들러
    private val naverHandler = @SuppressLint("HandlerLeak")
    object: OAuthLoginHandler(){
        override fun run(success: Boolean) {
            dismissLoadingDialog()

            if(success){
                val accessToken = naverModule.getAccessToken(mContext)

                Log.d("태그", "네이버 서버에 로그인 요청 성공, accessToken: $accessToken")


                // 팀 서버에 요청
                LogInService(mContext as LogInActivityView).tryNaverLogIn(accessToken)
            } else{
                val errorCode: String = naverModule.getLastErrorCode(mContext).code
                val errorDesc: String = naverModule.getLastErrorDesc(mContext)  // 실패 이유
                Log.d("로그", "네이버에 서버에서 실패 - errorCode: $errorCode ," +
                        " errorDesc: $errorDesc")
                showCustomToast("로그인을 다시 시도해주세요 - 원인: $errorDesc")

            }
        }

    }

    // 팀 서버와 네이버 로그인 통신을 성공
    override fun onPostNaverLogInSuccess(response: NaverResponse){
        Log.d("로그", "onPostKakaoLogInSuccess() called, response: $response")
        dismissLoadingDialog()

        /*when(response.message){
            "success" -> {
                saveUserLogIn(response.response!!)
            }
        }*/

        val mainIntent = Intent(this, MainActivity::class.java)
        val inputProfileIntent = Intent(this, InputProfileActivity::class.java)
    }

    // 팀 서버와 네이버 로그인 통신 실패
    override fun onPostNaverLogInFailure(message: String){
        Log.d("로그", "onPostNaverLogInFailure() called, message: $message")

        dismissLoadingDialog()
        showCustomToast("네트워크 확인 후 다시 시도해주세요.")
    }
}
