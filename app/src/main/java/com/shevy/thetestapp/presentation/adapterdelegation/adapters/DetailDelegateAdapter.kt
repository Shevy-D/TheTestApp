package com.shevy.thetestapp.presentation.adapterdelegation.adapters

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.shevy.thetestapp.data.model.detail.Detail
import com.shevy.thetestapp.databinding.DetailContainerBinding
import com.shevy.thetestapp.presentation.adapterdelegation.delegate.ViewBindingDelegateAdapter

class DetailDelegateAdapter: ViewBindingDelegateAdapter<Detail, DetailContainerBinding>(DetailContainerBinding::inflate) {
    @SuppressLint("SetTextI18n")
    override fun DetailContainerBinding.onBind(item: Detail) {
        Glide.with(imageDetails)
            .load(item.images)
            .into(imageDetails)
    }

    override fun isForViewType(item: Any): Boolean = item is Detail

    override fun Detail.getItemId(): Any = id
}