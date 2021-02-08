package com.rp2.star.airbnb.src.logIn

import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivityLogInBinding
import com.rp2.star.airbnb.src.logIn.phone.PhoneActivity
import java.util.regex.Pattern


class LogInActivity : BaseActivity<ActivityLogInBinding>(ActivityLogInBinding::inflate){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 전화번호 입력할 때 형식 검사 -> 에러 메시지, 계속하기 버튼 활성화
        binding.logInTextInputNum.addTextChangedListener(numChangedListener)

        // 계속하기 버튼 클릭
        binding.logInBtnContinue.setOnClickListener(continueOnClickListener)
        binding.logInBtnContinue.isEnabled = false
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
            var returnValue = false;

            try {
                val regex =
                    "^\\s*(02|031|032|033|041|042|043|051|052|053|054|055|061|062|063|064|070)" +
                            "?(-|\\)|\\s)*(\\d{3,4})(-|\\s)*(\\d{4})\\s*$"
                val p = Pattern.compile(regex);
                val m = p.matcher(phoneNumber);
                if (m.matches()) {
                    returnValue = true;
                }
                if(returnValue){
                    if(phoneNumber.split('-').size != 3){
                        returnValue = false
                    }
                }
                return returnValue;
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

        phoneIntent.putExtra("phone",phoneNumber)
        phoneIntent.putExtra("formatted", formattedNumber)
        startActivity(phoneIntent)
    }


}
