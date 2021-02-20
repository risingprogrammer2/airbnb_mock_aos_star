package com.rp2.star.airbnb.src.main.search.searching.company2

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.databinding.FragmentSearchingCompany2Binding
import com.rp2.star.airbnb.src.main.search.searching.SearchingActivityView
import com.rp2.star.airbnb.util.LoadingDialog


class SearchingCompany2Fragment(val searchingView: SearchingActivityView):
    BottomSheetDialogFragment() {
    private var _binding: FragmentSearchingCompany2Binding? = null
    private val binding get() = _binding!!
    lateinit var mLoadingDialog: LoadingDialog

    private val sp = ApplicationClass.sSharedPreferences
    private var adult: Int = 1
    private var kid: Int = 0
    private var child: Int = 0
    private var total: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchingCompany2Binding.inflate(inflater, container, false)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CardTheme)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchingCompany2Btn1Number.text = "1"


        // 완료 버튼
        binding.searchingCompany2Save.setOnClickListener{
            sp.edit().apply{
                putInt("adult", adult)
                putInt("kid", kid)
                putInt("child", child)
                total = adult + kid
                putInt("total", total)
                commit()
            }
            this.dismiss()
        }


        // 뒤로가기 버튼
        binding.searchingCompany2Back.setOnClickListener {
            this.dismiss()
        }

        // 삭제 버튼
        binding.searchingCompany2Erase.setOnClickListener {
            binding.searchingCompany2Btn1Number.text = "1"
            binding.searchingCompany2Btn2Number.text = "0"
            binding.searchingCompany2Btn3Number.text = "0"
            adult = 1
            kid = 0
            child = 0
        }


        // 인원 버튼 리스너 등록
        for(i in 0..2){
            val idx = i+1
            val minusId = resources.getIdentifier("searching_company2_btn${idx}_minus",
                "id", activity!!.packageName)
            val numId = resources.getIdentifier("searching_company2_btn${idx}_number",
                "id", activity!!.packageName)
            val plusId = resources.getIdentifier("searching_company2_btn${idx}_plus",
                "id", activity!!.packageName)
            Log.d("로그","minusId: $minusId , numId: $numId , plusId: $plusId")

            val minusBtn: TextView = binding.root.findViewById(minusId)
            val numText: TextView = binding.root.findViewById(numId)
            val plusBtn: TextView = binding.root.findViewById(plusId)

            // 마이너스 버튼
            minusBtn.setOnClickListener {
                when(idx) {
                    // 성인
                    0 -> {
                        if(adult > 0){
                            adult -= 1
                            total -= 1
                            numText.text = String.format("%d", adult)
                        }
                    }
                    // 어린이
                    1 -> {
                        if(kid > 0){
                            kid -= 1
                            total -=1
                            numText.text = String.format("%d", kid)
                        }
                    }
                    // 유아
                    2 -> {
                        if(child > 0){
                            child -= 1
                            total -=1
                            numText.text = String.format("%d", child)
                        }
                    }
                }

            }

            // 플러스버튼
            plusBtn.setOnClickListener {
                when(idx) {
                    0 -> {
                        adult += 1
                        total +=1
                        numText.text = String.format("%d", adult)
                    }
                    1 -> {
                        kid += 1
                        total +=1
                        numText.text = String.format("%d", kid)
                    }
                    2 -> {
                        child += 1
                        total +=1
                        numText.text = String.format("%d", child)
                    }
                }
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showCustomLongToast(message: String){
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}