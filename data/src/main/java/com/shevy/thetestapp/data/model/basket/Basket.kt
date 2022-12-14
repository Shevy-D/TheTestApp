package com.shevy.thetestapp.data.model.basket

import com.shevy.thetestapp.domain.model.basket.HomeStoreBasket

data class Basket(
    val basket: List<HomeStoreBasket>,
    val delivery: String,
    val id: String,
    val total: Int
)