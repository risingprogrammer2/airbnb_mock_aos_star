package com.rp2.star.airbnb.src.main.store.on_store

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.textfield.TextInputEditText
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.databinding.FragmentOnStoreBtmBinding
import com.rp2.star.airbnb.src.main.search.searching.show.SearchingShowFragment
import com.rp2.star.airbnb.src.main.search.searching.show.SearchingShowService
import com.rp2.star.airbnb.src.main.store.StoreFragmentView
import com.rp2.star.airbnb.src.main.store.models.GetFoldersResponse
import com.rp2.star.airbnb.src.main.store.models.Saves
import com.rp2.star.airbnb.util.LoadingDialog


class OnStoreBtmFragment():
    BottomSheetDialogFragment(), StoreFragmentView, OnStoreView{
    private var _binding: FragmentOnStoreBtmBinding? = null
    private val binding get() = _binding!!
    lateinit var mLoadingDialog: LoadingDialog

    private val sp = ApplicationClass.sSharedPreferences
    private var userId: Int = -1

    private var folderList = ArrayList<Saves>()
    private lateinit var onStoreRecyclerAdapter: OnStoreRecyclerAdapter
    private var lodgeId = -1    // 숙소 아이디
    private var position = -1   // 리사이클러뷰에서 숙소 position

    lateinit var createFolderDialog: Dialog


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("로그","onCreateDialog() called")
        val btmSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // 다이얼로그에 쓸 화면을 View로 생성해서, 다이얼로그에 적용한다
        val view = View.inflate(context, R.layout.fragment_on_store_btm, null)
        btmSheetDialog.setContentView(view)

        // 새로운 폴더 만들기 버튼을 BottomSheetDialogFragment의 최상위 레이아웃인 FrameLayout에 추가한다
        val container = view.parent.parent.parent as FrameLayout
        val createFolderBtn = View.inflate(context, R.layout.layout_create_folder,
            null
        )
        val cParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        cParams.gravity = Gravity.BOTTOM

        (createFolderBtn?.findViewById<Button>(R.id.on_store_btn_new)
                )?.setOnClickListener(onClickNew)
        container.addView(createFolderBtn, container.childCount, cParams)
        // container.getChildAt(container.childCount-1).setOnClickListener(onClickNew)

        //setting Peek at the 16:9 ratio keyline of its parent.
        val btmSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        btmSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        // 위에랑 같은 동작을 하는 코드
        /*val btmSheetParent = btmSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
        val params = btmSheetParent?.layoutParams
        params?.height = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        btmSheetParent?.layoutParams = params*/

        // val screenHeight = activity!!.resources.displayMetrics.heightPixels

        return btmSheetDialog
    }

    override fun storeLodge(folderName: String){
        SearchingShowService(parentFragment as SearchingShowFragment).tryPostStoreLodge(
            folderName, lodgeId, position
        )
    }

    private val onClickNew = View.OnClickListener {
        createFolderDialog = Dialog(parentFragment?.context!!, R.style.DialogNewFolder)
        Log.d("로그", "parentFragment: $parentFragment , activity: ${parentFragment?.activity}" +
                "context: ${parentFragment?.context}")
        createFolderDialog.setContentView(R.layout.dialog_new_folder)

        // 목록 만들기 버튼 클릭 -> 목록 만들고 그곳에 바로 저장
        val btnCreateFolder = createFolderDialog.findViewById<Button>(R.id.new_folder_btn_new)
        btnCreateFolder.setOnClickListener {
            val editText = createFolderDialog.findViewById<TextInputEditText>(R.id.new_folder_text)
            val folderName = editText.text.toString()
            val searchingShowFragment = parentFragment as SearchingShowFragment

            // 숙소 저장 API 호출
            storeLodge(folderName)
        }
        createFolderDialog.show()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("로그","onViewCreated() called")
        super.onViewCreated(view, savedInstanceState)

        userId = sp.getInt("id", -1)

        // 클릭한 숙소 아이디
        lodgeId = arguments?.getInt("lodgeId")!!
        position = arguments?.getInt("position")!!

        // 서버 못써서 주석처리

//        showLoadingDialog(context!!)
//        StoreService(this).tryGetStoredFolders()

        val imgList = arrayListOf<String>("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile25.uf.tistory.com%2Fimage%2F99086B3C5B9B75C431B0FD")
        val saves = Saves("더미데이터", imgList)
        folderList.add(saves)
        onStoreRecyclerAdapter = OnStoreRecyclerAdapter(context!!, folderList, this)
        binding.onStoreRecyclerView.adapter = onStoreRecyclerAdapter
        binding.onStoreRecyclerView.layoutManager = LinearLayoutManager(
            activity!!,
            LinearLayoutManager.VERTICAL, false
        )


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnStoreBtmBinding.inflate(inflater, container, false)
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CardTheme)
        binding.root.minimumHeight = Resources.getSystem().displayMetrics.heightPixels / 2
        return binding.root
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

    override fun onGetFoldersSuccess(response: GetFoldersResponse) {
        Log.d("로그", "onGetFoldersSuccess() called - response: $response")
        dismissLoadingDialog()

        when(response.isSuccess){
            true -> {
                Log.d("로그", "isSuccess: true - message: ${response.message}")

                folderList = response.result.folder
                onStoreRecyclerAdapter = OnStoreRecyclerAdapter(context!!, folderList, this)
                binding.onStoreRecyclerView.adapter = onStoreRecyclerAdapter
                binding.onStoreRecyclerView.layoutManager = LinearLayoutManager(
                    activity!!,
                    LinearLayoutManager.VERTICAL, false
                )
            }

            false -> {
                showCustomLongToast(
                    "폴더를 불러오지 못했습니다, 다음을 확인해주세요.\n" +
                            "${response.message}"
                )
                dismiss()
                // (activity!! as MainActivity).comeBackToSearch()
            }
        }
    }

    override fun onGetFoldersFailure(message: String) {
        Log.d("로그", "onGetFoldersFailure() called - message: $message")
        dismissLoadingDialog()

        showCustomToast("네트워크 확인 후 다시 시도해주세요.\nmessage: $message")
        dismiss()
    }
}