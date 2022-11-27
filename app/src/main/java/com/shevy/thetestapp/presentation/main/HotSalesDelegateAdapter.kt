package com.shevy.thetestapp.presentation.main

import android.annotation.SuppressLint
import android.view.View
import com.bumptech.glide.Glide
import com.shevy.thetestapp.domain.model.products.HomeStoreProducts
import com.shevy.thetestapp.databinding.HotSalesContainerBinding
import com.shevy.thetestapp.presentation.adapterdelegation.ViewBindingDelegateAdapter

class HotSalesDelegateAdapter: ViewBindingDelegateAdapter<HomeStoreProducts, HotSalesContainerBinding>(HotSalesContainerBinding::inflate) {

    @SuppressLint("SetTextI18n")
    override fun HotSalesContainerBinding.onBind(item: HomeStoreProducts) {
        textTitle.text = item.title
        textDescription.text = item.subtitle
        Glide.with(imageHotSale.context)
            .load(item.picture)
            .into(imageHotSale)

        if (!item.is_new) {
            boxNew.visibility = View.GONE
        }
    }

    override fun isForViewType(item: Any): Boolean = item is HomeStoreProducts

    override fun HomeStoreProducts.getItemId(): Any = id
}