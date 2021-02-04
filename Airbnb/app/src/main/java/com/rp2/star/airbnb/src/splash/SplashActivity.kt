package com.rp2.star.airbnb.src.splash

import android.os.Bundle
import com.rp2.star.airbnb.config.BaseActivity
import com.rp2.star.airbnb.databinding.ActivitySplashBinding

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)*/
    }
}
