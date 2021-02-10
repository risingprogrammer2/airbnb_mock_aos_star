package com.rp2.star.airbnb.src.log_in.input_profile

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivityInputProfileBinding
import com.rp2.star.airbnb.src.log_in.models.PostPhoneSignUpRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import java.util.*


class InputProfileActivity : BaseActivity<ActivityInputProfileBinding>(ActivityInputProfileBinding::inflate),
InputProfileActivityView{

    private lateinit var lastName: String
    private lateinit var firstName: String
    private lateinit var mYear: String
    private lateinit var mMonth: String
    private lateinit var mDate: String
    private lateinit var birth: String
    private lateinit var email: String
    private lateinit var phoneNumber: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        phoneNumber = intent.extras?.get("phone").toString()

        // 이름 입력칸에 따라 포커스 이동
        binding.inputProfileTextFirst.setOnFocusChangeListener { view, hasFocus ->
            when(hasFocus){
                true -> {
                    binding.inputProfileNameDivider.visibility = View.GONE
                }
                false -> {
                    if(currentFocus != binding.inputProfileTextLast &&
                        currentFocus != binding.inputProfileLayoutLast){
                        binding.inputProfileNameDivider.visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.inputProfileTextLast.setOnFocusChangeListener { view, hasFocus ->
            when(hasFocus){
                true -> {
                    binding.inputProfileNameDivider.visibility = View.GONE
                }
                false -> {
                    if(currentFocus != binding.inputProfileTextFirst &&
                        currentFocus != binding.inputProfileLayoutFirst){
                        binding.inputProfileNameDivider.visibility = View.VISIBLE
                    }
                }
            }
        }

        // 생년월일 입력 터치 -> datePicker dialog
        // datePicker dialog 세팅
        val calendar = Calendar.getInstance()
        val dateDialog = DatePickerDialog(this, R.style.DatePickerStyle, dateDialogListener,
            calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE))
        dateDialog.setTitle(R.string.input_profile_text_date_picker)
        val positiveButton = dateDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
        // positiveButton.setTextColor(resources.getColor(R.color.redForDialogPositive))
        binding.inputProfileTextBirth.setOnFocusChangeListener { view, hasFocus ->
            Log.d("로그", "setOnFocusChangeListener, hasFocus: $hasFocus")
            if(hasFocus){
                dateDialog.show()
            }
        }
        binding.inputProfileLayoutBirth.setOnFocusChangeListener { view, hasFocus ->
            Log.d("로그", "setOnFocusChangeListener, hasFocus: $hasFocus")
            if(hasFocus){
                dateDialog.show()
            }
        }

        // 정보가 다 입력되어 있으면 버튼 활성화
        binding.inputProfileTextFirst.addTextChangedListener(firstChangeListener)
        binding.inputProfileTextLast.addTextChangedListener(lastChangeListener)
        binding.inputProfileTextBirth.addTextChangedListener(birthChangeListener)
        binding.inputProfileTextEmail.addTextChangedListener(emailChangeListener)

        // 계속하기 버튼
        binding.inputProfileBtnContinue.setOnClickListener{
            lastName = binding.inputProfileTextLast.text.toString()
            firstName = binding.inputProfileTextFirst.text.toString()

//            val lastIdx = mYear.lastIndex
//            val year = mYear.substring(lastIdx-1..lastIdx)
            birth = mYear + mMonth + mDate

            email = binding.inputProfileTextEmail.text.toString()

            val params = PostPhoneSignUpRequest(phoneNumber, email, lastName, firstName,
            birth, "123456789")
            Log.d("로그", "회원가입 정보: $params")
        }

    }

    // 날짜 선택한 것을 텍스트뷰에 반영
    private val dateDialogListener =
        DatePickerDialog.OnDateSetListener { datePicker, year, month, date ->
            Log.d("로그", "onBirthSet: year: $year, month: ${month+1}, day: $date")

            mYear = year.toString()
            mMonth = (month+1).toString()
            mDate = date.toString()
            if(mMonth.toInt() < 10){
                mMonth = "0$month"
            }
            if(date < 10){
                mDate = "0$date"
            }
            binding.inputProfileTextBirth.setText("${mMonth}/${mDate}/${mYear}")
        }

    // 이메일 입력에 따라 오류 색상 적용
    private val emailChangeListener = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(s!!.isNotEmpty()){
                if(!s.contains('@')){
                    binding.inputProfileLayoutEmail.error = " "
                    binding.inputProfileLayoutEmail.setBackgroundResource(R.drawable.log_in_text_phone_border_error)
                    binding.inputProfileTextNotice3.visibility = View.GONE
                    binding.inputProfileTextEmailError.visibility = View.VISIBLE
                }else{
                    binding.inputProfileLayoutEmail.error = null
                    binding.inputProfileLayoutEmail.setBackgroundResource(R.drawable.input_profile_text_selector)
                    binding.inputProfileTextNotice3.visibility = View.VISIBLE
                    binding.inputProfileTextEmailError.visibility = View.GONE
                }
            }

            if(binding.inputProfileLayoutEmail.error == null &&
                binding.inputProfileTextFirst.text!!.isNotEmpty() &&
                binding.inputProfileTextLast.text!!.isNotEmpty() &&
                    binding.inputProfileTextBirth.text!!.isNotEmpty()){
                binding.inputProfileBtnContinue.isEnabled = true
            }else{
                binding.inputProfileBtnContinue.isEnabled = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    // 정보 텍스트가 모두 입력되어 있으면 버튼 활성화
    private val birthChangeListener = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(binding.inputProfileLayoutEmail.error == null &&
                binding.inputProfileTextFirst.text!!.isNotEmpty() &&
                binding.inputProfileTextLast.text!!.isNotEmpty() &&
                    binding.inputProfileTextBirth.text!!.isNotEmpty()){
                binding.inputProfileBtnContinue.isEnabled = true
            }else{
                binding.inputProfileBtnContinue.isEnabled = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
    private val lastChangeListener = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(binding.inputProfileLayoutEmail.error == null &&
                binding.inputProfileTextFirst.text!!.isNotEmpty() &&
                binding.inputProfileTextLast.text!!.isNotEmpty() &&
                    binding.inputProfileTextBirth.text!!.isNotEmpty()){
                binding.inputProfileBtnContinue.isEnabled = true
            }else{
                binding.inputProfileBtnContinue.isEnabled = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
    private val firstChangeListener = object: TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if(binding.inputProfileLayoutEmail.error == null &&
                binding.inputProfileTextFirst.text!!.isNotEmpty() &&
                binding.inputProfileTextLast.text!!.isNotEmpty() &&
                    binding.inputProfileTextBirth.text!!.isNotEmpty()){
                binding.inputProfileBtnContinue.isEnabled = true
            }else{
                binding.inputProfileBtnContinue.isEnabled = true
            }
        }

        override fun afterTextChanged(p0: Editable?) {}
    }

    override fun onPostSignUpSuccess(response: SignUpResponse) {
        when(response.success){
            true -> {
                Log.d("로그", "onPostSignUpSuccess() called -> message: $response.message")
                //val mainIntent =

            }
            false -> {
                response.message?.let { showCustomLongToast(it) }
            }
        }

    }

    override fun onPostSignUpFailure(message: String) {
        showCustomToast("네트워크 통신 오류: $message")
    }

}
