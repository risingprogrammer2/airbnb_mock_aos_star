package com.rp2.star.airbnb.src.main.my_page

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentMainMyPageBinding
import com.rp2.star.airbnb.databinding.FragmentMainMyPageBinding.bind
import com.rp2.star.airbnb.src.log_in.LogInActivity


class MyPageFragment : BaseFragment<FragmentMainMyPageBinding>(FragmentMainMyPageBinding::bind,
    R.layout.fragment_main_my_page){

    private val sp = ApplicationClass.sSharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.myPageImg.clipToOutline = true
        val firstName = sp.getString("firstName" ,null)
        val img = sp.getString("img", null)

        Log.d("로그","마이페이지 - firstName: $firstName")
        Log.d("로그","마이페이지 - img: $img")
        if(firstName != null) {
            binding.myPageName.text = firstName
        }
        if(img != null){
            Glide.with(this)
                .load(img)
                .placeholder(R.drawable.my_page_me)
                .error(R.drawable.my_page_me)
                .into(binding.myPageImg)
        }

        binding.myPageBtn14.setOnClickListener {
            appLogOut()
            val intent = Intent(context, LogInActivity::class.java)
            startActivity(intent)
            activity!!.finish()
        }

    }
}