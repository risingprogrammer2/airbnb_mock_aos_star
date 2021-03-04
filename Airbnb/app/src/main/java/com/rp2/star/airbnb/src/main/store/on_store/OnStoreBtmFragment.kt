package com.rp2.star.airbnb.src.main.store.on_store

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.ApplicationClass
import com.rp2.star.airbnb.databinding.FragmentOnStoreBtmBinding
import com.rp2.star.airbnb.src.main.store.StoreFragmentView
import com.rp2.star.airbnb.src.main.store.StoreService
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
        /*val metrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getRealMetrics(metrics)
        btmSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        btmSheetDialog.behavior.peekHeight = metrics.heightPixels*/
        val view = View.inflate(context, R.layout.fragment_on_store_btm, null)
        // val btmSheet = btmSheetDialog.findViewById<View>(R.id.on_store_fragment)
        btmSheetDialog.setContentView(view)
        val btmSheetBehavior = BottomSheetBehavior.from(view.parent as View)
        Log.d("로그","view.parent: ${view.parent}")

        //setting Peek at the 16:9 ratio keyline of its parent.
        btmSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

        setUpFullHeight(view.parent as View)

        // view.minimumHeight = (Resources.getSystem().displayMetrics.heightPixels) / 2

        btmSheetBehavior.addBottomSheetCallback(btmSheetCallback)

        /*btmSheetDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            val btmSheet = dialog.findViewById<View>(R.id.on_store_fragment)
            //setUpFullHeight(btmSheet!!.rootView)
            val btmSheetBehavior = BottomSheetBehavior.from(btmSheet!!.parent as View)
            Log.d("로그", "parent: $view")
            //setting Peek at the 16:9 ratio keyline of its parent.
            btmSheetBehavior.peekHeight = BottomSheetBehavior.PEEK_HEIGHT_AUTO

            // btmSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED

            btmSheetBehavior.isHideable = true
            btmSheetBehavior.isFitToContents = false

            btmSheetBehavior.addBottomSheetCallback(btmSheetCallback)
        }*/
        return btmSheetDialog
    }

    /*override fun onStart() {
        super.onStart()
        val dialog = dialog;
        lateinit var bottomSheet: View

        if (dialog != null) {
            bottomSheet = dialog.findViewById(R.id.on_store_fragment);
            bottomSheet.layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        }
        val view = view;
        view!!.post {
            val parent = view.parent
            val params = (parent as View).layoutParams as CoordinatorLayout.LayoutParams
            val behavior = params.behavior
            val bottomSheetBehavior = behavior as BottomSheetBehavior<*>
            bottomSheetBehavior.peekHeight = view.measuredHeight;
            (bottomSheet.parent as View).setBackgroundColor(Color.TRANSPARENT)
        }
    }*/

   private val btmSheetCallback = object: BottomSheetBehavior.BottomSheetCallback(){
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            Log.d("로그", "onStateChanged() called, btmSheet: $bottomSheet , newState: $newState")

        }
        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            Log.d("로그", "onSlide() called, btmSheet: $bottomSheet , slideOffset: $slideOffset")
        }
    }

    private fun setUpFullHeight(bottomSheet: View){
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("로그","onViewCreated() called")
        super.onViewCreated(view, savedInstanceState)

        userId = sp.getInt("id", -1)

        showLoadingDialog(context!!)
        StoreService(this).tryGetStoredFolders()

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