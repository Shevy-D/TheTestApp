package com.shevy.thetestapp.presentation.detail

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.shevy.thetestapp.databinding.DetailContainerBinding
import com.shevy.thetestapp.presentation.adapterdelegation.ViewBindingDelegateAdapter

class DetailDelegateAdapter: ViewBindingDelegateAdapter<String, DetailContainerBinding>(DetailContainerBinding::inflate) {
    @SuppressLint("SetTextI18n")
    override fun DetailContainerBinding.onBind(item: String) {
        Glide.with(imageDetails)
            .load(item)
            .into(imageDetails)
    }

    override fun isForViewType(item: Any): Boolean = item is String

    override fun String.getItemId(): Any {
        return 0
    }

}