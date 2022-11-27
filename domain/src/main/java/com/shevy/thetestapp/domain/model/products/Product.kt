package com.shevy.thetestapp.domain.model.products

import com.shevy.thetestapp.domain.model.products.BestSeller
import com.shevy.thetestapp.domain.model.products.HomeStoreProducts

data class Product(
    val best_seller: List<BestSeller>,
    val home_store: List<HomeStoreProducts>
)