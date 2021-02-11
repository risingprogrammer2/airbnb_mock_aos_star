package com.rp2.star.airbnb.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivitySplashBinding
import com.rp2.star.airbnb.src.log_in.LogInActivity
import com.rp2.star.airbnb.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sp = ApplicationClass.sSharedPreferences
        val idx = sp.getInt("idx", -1)

        // 임시 설정: 카카오 로그아웃
        kakaoLogOut()

        Handler(Looper.getMainLooper()).postDelayed({
            // 로그인 되어있지 않으면 로그인 액티비티로 이동
            if(idx == -1){
                startActivity(Intent(this, LogInActivity::class.java))
            }
            // 로그인 되어있으면 메인 액티비티로 이동
            else{
                startActivity(Intent(this, MainActivity::class.java))
            }
            finish()
        }, 1200)



    }
}
