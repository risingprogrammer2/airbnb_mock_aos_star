package com.rp2.star.airbnb.src.main.store.on_store

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.databinding.FragmentOnStoreBtmBinding
import com.rp2.star.airbnb.src.main.store.StoreFragmentView
import com.rp2.star.airbnb.src.main.store.models.GetFoldersResponse
import com.rp2.star.airbnb.src.main.store.models.Saves
import com.rp2.star.airbnb.util.LoadingDialog


class OnStoreBtmFragment():
    BottomSheetDialogFragment(), StoreFragmentView{
    private var _binding: FragmentOnStoreBtmBinding? = null
    private val binding get() = _binding!!
    lateinit var mLoadingDialog: LoadingDialog

    private val sp = ApplicationClass.sSharedPreferences
    private var userId: Int = -1

    private var folderList = ArrayList<Saves>()
    private lateinit var onStoreRecyclerAdapter: OnStoreRecyclerAdapter


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d("로그","onCreateDialog() called")
        val btmSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        val view = View.inflate(context, R.layout.fragment_on_store_btm, null)


        btmSheetDialog.setContentView(view)
        Log.d("로그", "btmSheetDialog: $btmSheetDialog")

        val btmSheetBehavior = BottomSheetBehavior.from(view.parent as View)

        // 새로운 폴더 만들기 버튼을 BottomSheetDialogFragment의 최상위 레이아웃인 FrameLayout에 추가한다
        val container = view.parent.parent.parent as FrameLayout
        val createFolderView = View.inflate(context, R.layout.layout_create_folder,
            null
        )
        val cParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        )
        cParams.gravity = Gravity.BOTTOM

        createFolderView?.setOnClickListener(onClickNew)
        container.addView(createFolderView, container.childCount, cParams)
        // container.addView(createFolderView, cParams)




        //setting Peek at the 16:9 ratio keyline of its parent.
        btmSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        val screenHeight = activity!!.resources.displayMetrics.heightPixels
        /*val params = ((view.parent as View).layoutParams) as CoordinatorLayout.LayoutParams
        val behavior2 = params.behavior*/

        // setUpFullHeight(view.parent as View)
        val btmSheetContainer = btmSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
        val params = btmSheetContainer?.layoutParams
        params?.height = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        //params?.height = screenHeight / 2
        btmSheetContainer?.layoutParams = params


        // minimizeHeight(view.parent as View)


        /*btmSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback(){
            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                Log.d("로그", "onSlide() called, btmSheet: $bottomSheet , slideOffset: $slideOffset")
            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                Log.d("로그", "STATE_COLLAPSED, bottomSheet: $bottomSheet ," +
                        " height: ${bottomSheet.layoutParams.height}")

                when(newState){
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        btmSheetContainer?.layoutParams = params
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        setUpFullHeight(btmSheetContainer!!)
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                        btmSheetContainer?.layoutParams = params
                    }
                }
            }
        })*/

        return btmSheetDialog
    }

    private val onClickNew = View.OnClickListener {

    }
    private fun setUpFullHeight(bottomSheetParent: View){
        val layoutParams = bottomSheetParent.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheetParent.layoutParams = layoutParams
        Log.d("로그","setUpFullHeight() called - height1: ${bottomSheetParent.layoutParams.height}, " +
                "height2: ${bottomSheetParent.height}")
    }

    private fun minimizeHeight(bottomSheetParent: View){
        val layoutParams = bottomSheetParent.layoutParams
        layoutParams.height = BottomSheetBehavior.PEEK_HEIGHT_AUTO
        bottomSheetParent.layoutParams = layoutParams
        Log.d("로그","minimizeHeight() called - height1: ${bottomSheetParent.layoutParams.height}" +
                "height2: ${bottomSheetParent.height}")
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("로그","onViewCreated() called")
        super.onViewCreated(view, savedInstanceState)

        userId = sp.getInt("id", -1)

        // 서버 못써서 주석처리

//        showLoadingDialog(context!!)
//        StoreService(this).tryGetStoredFolders()

        val imgList = arrayListOf<String>("https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=http%3A%2F%2Fcfile25.uf.tistory.com%2Fimage%2F99086B3C5B9B75C431B0FD")
        val saves = Saves("더미데이터", imgList)
        folderList.add(saves)
        onStoreRecyclerAdapter = OnStoreRecyclerAdapter(context!!, folderList)
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
                onStoreRecyclerAdapter = OnStoreRecyclerAdapter(context!!, folderList)
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