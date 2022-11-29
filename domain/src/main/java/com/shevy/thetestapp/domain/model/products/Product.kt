package com.shevy.thetestapp.domain.model.products

data class Product(
    val best_seller: List<BestSeller>,
    val home_store: List<HomeStoreProducts>
)