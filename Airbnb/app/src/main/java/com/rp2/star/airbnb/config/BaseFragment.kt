package com.rp2.star.airbnb.config

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.kakao.sdk.user.UserApiClient
import com.rp2.star.airbnb.src.log_in.models.ResultSignUp
import com.rp2.star.airbnb.util.LoadingDialog

// Fragment의 기본을 작성, 뷰 바인딩 활용
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    lateinit var mLoadingDialog: LoadingDialog

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
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

    // 토스트를 길게 띄움.
    fun showCustomLongToast(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }

    // 로그인 정보 저장
    fun saveUserLogIn(result: ResultSignUp){
        val id: Int = result.id
        val jwt: String =result.token
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

    // status bar 배경 색상 변경
    fun changeStatusBarColor(color: Int){
        val mWindow = requireActivity().window
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // mWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        mWindow.statusBarColor = color

    }
}