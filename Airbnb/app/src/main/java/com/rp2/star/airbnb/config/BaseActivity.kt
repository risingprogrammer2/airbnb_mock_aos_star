package com.rp2.star.airbnb.config

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.google.gson.GsonBuilder
import com.kakao.sdk.user.UserApiClient
import com.rp2.star.airbnb.src.log_in.models.ResultSignUp
import com.rp2.star.airbnb.util.LoadingDialog

// 액티비티의 기본을 작성, 뷰 바인딩 활용
abstract class BaseActivity<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) :
    AppCompatActivity() {
    protected lateinit var binding: B
        private set
    lateinit var mLoadingDialog: LoadingDialog

    // 뷰 바인딩 객체를 받아서 inflate해서 화면을 만들어줌.
    // 즉 매번 onCreate에서 setContentView를 하지 않아도 됨.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    // 띄워 놓은 로딩 다이얼로그를 없앰.
    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    // 토스트를 쉽게 띄울 수 있게 해줌.
    fun showCustomToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    // 토스트를 길게 띄움.
    fun showCustomLongToast(message: String){
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    // 로그인할 때 유저 정보 저장
    fun saveUserLogIn(result: ResultSignUp){
        val id: Int = result.id
        val jwt: String =result.token
        var name: String? = null
        UserApiClient.instance.me{ user, error ->
            if (error != null) {
                Log.e("로그", "사용자 정보 요청 실패", error)
            }
            else if (user != null) {
                Log.i("로그", "사용자 정보 요청 성공, user: $user")
                val gson = GsonBuilder().create()
                val profile = user.kakaoAccount?.profile
                Log.d("로그", "카카오 프로필: $profile")
            }

        }

        val sp = ApplicationClass.sSharedPreferences
        val spEditor = sp.edit()
        spEditor.putInt("id", id)
        spEditor.putString("jwt", jwt)
        spEditor.apply()
    }


    // 카카오연결 끊기 - 다시 카카오톡 화면 띄워서 로그인 하는 모습 보이기 위해 로그아웃용으로 사용
    fun kakaoLogOut(){
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("로그", "연결 끊기 실패", error)
            }
            else {
                Log.i("로그", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }


    /*
    // 카카오 로그아웃
    fun kakaoLogOut(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Log.e("로그", "로그아웃 실패. SDK에서 토큰 삭제됨", error)
            }
            else {
                Log.i("로그", "로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }
    */

    // 네이버 로그아웃
    fun naverLogOut(){
        ApplicationClass.naverLoginModule.logout(applicationContext)
    }

    // 네이버 연동 해제
    fun naverDelete(){
        val isSuccessDeleteToken: Boolean = ApplicationClass.
        naverLoginModule.logoutAndDeleteToken(applicationContext)

        if (!isSuccessDeleteToken) {
            // 서버에서 토큰 삭제에 실패했어도 클라이언트에 있는 토큰은 삭제되어 로그아웃된 상태입니다.
            // 클라이언트에 토큰 정보가 없기 때문에 추가로 처리할 수 있는 작업은 없습니다.
            Log.d("로그", "네이버 연동 해제 errorCode:" +
                    ApplicationClass.naverLoginModule.getLastErrorCode(applicationContext))
            Log.d("로그", "네이버 연동 해제 errorDesc:" +
                    ApplicationClass.naverLoginModule.getLastErrorDesc(applicationContext))
        }
    }
}