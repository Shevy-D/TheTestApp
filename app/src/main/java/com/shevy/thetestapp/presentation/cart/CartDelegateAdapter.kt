package com.shevy.thetestapp.presentation.cart

import android.annotation.SuppressLint
import com.bumptech.glide.Glide
import com.shevy.thetestapp.data.model.basket.HomeStoreBasket
import com.shevy.thetestapp.databinding.CartContainerBinding
import com.shevy.thetestapp.presentation.adapterdelegation.ViewBindingDelegateAdapter

class CartDelegateAdapter: ViewBindingDelegateAdapter<HomeStoreBasket, CartContainerBinding>(CartContainerBinding::inflate) {
    @SuppressLint("SetTextI18n")
    override fun CartContainerBinding.onBind(item: HomeStoreBasket) {
        titleCart.text = item.title
        priceCart.text = "$${item.price}.00"
        Glide.with(imageCart.context)
            .load(item.images)
            .into(imageCart)
    }

    override fun isForViewType(item: Any): Boolean = item is HomeStoreBasket

    override fun HomeStoreBasket.getItemId(): Any = id
}