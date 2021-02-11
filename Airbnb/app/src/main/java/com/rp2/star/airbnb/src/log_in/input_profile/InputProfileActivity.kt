package com.rp2.star.airbnb.src.log_in.input_profile

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivityInputProfileBinding
import com.rp2.star.airbnb.src.log_in.models.PostPhoneSignUpRequest
import com.rp2.star.airbnb.src.log_in.models.SignUpResponse
import com.rp2.star.airbnb.src.main.MainActivity
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
        // val positiveButton = dateDialog.getButton(DatePickerDialog.BUTTON_POSITIVE)
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
            val sp = ApplicationClass.sSharedPreferences
            val id = sp.getInt("id", -1)

            // 소셜로그인으로 이미 서버에서 회원가입 등록 -> 다시 서버로 요청하지 않는다
            if(id != -1){
                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
            }

            // sms 회원가입 -> 서버로 정보를 전달해서 회원가입 요청
            else {
                lastName = binding.inputProfileTextLast.text.toString()
                firstName = binding.inputProfileTextFirst.text.toString()

                // +82로 시작하는 번호
                phoneNumber = intent.extras?.get("phone").toString()
                val lastIdx = mYear.lastIndex
                val year = mYear.substring(lastIdx - 1..lastIdx)
                birth = year + mMonth + mDate
                email = binding.inputProfileTextEmail.text.toString()


                // API에 전달할 Body parameters
                /*val params = PostPhoneSignUpRequest("+821012345678", email, lastName, firstName,
            birth)*/
                val params = PostPhoneSignUpRequest(phoneNumber, email, lastName, firstName, birth)
                Log.d("로그", "입력한 회원가입 정보: $params")

                showLoadingDialog(this)
                InputProfileService(this).tryPostSignUp(params)
            }

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
        dismissLoadingDialog()
        Log.d("로그", "onPostSignUpSuccess() called -> message: $response.message")

        when(response.success){
            // 회원가입이 성공하면 메인화면으로 넘어간다.
            true -> {
                // SharedPreference 에디트 모드로 가입된 idx과 token 값 저장
                // 이후 자동 로그인, 프로필 조회, 수정 등에 사용
                val sp = ApplicationClass.sSharedPreferences
                val editor = sp.edit()
                // idx와 token 값 저장
                response.result?.let {
                    editor.putInt("id", it.id)
                    editor.putString("token", it.token)
                    editor.commit()}

                val mainIntent = Intent(this, MainActivity::class.java)
                startActivity(mainIntent)
                finish()

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
