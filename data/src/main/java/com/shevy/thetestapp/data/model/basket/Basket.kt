package com.shevy.thetestapp.data.model.basket

data class Basket(
    val basket: List<HomeStoreBasket>,
    val delivery: String,
    val id: String,
    val total: Int
)