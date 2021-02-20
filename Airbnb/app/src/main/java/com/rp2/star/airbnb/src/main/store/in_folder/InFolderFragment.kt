package com.rp2.star.airbnb.src.main.store.in_folder

import android.os.Bundle
import android.view.View
import com.rp2.star.airbnb.R
import com.rp2.star.airbnb.config.BaseFragment
import com.rp2.star.airbnb.databinding.FragmentStoredInFolderBinding


class InFolderFragment : BaseFragment<FragmentStoredInFolderBinding>(FragmentStoredInFolderBinding::bind,
    R.layout.fragment_stored_in_folder){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}