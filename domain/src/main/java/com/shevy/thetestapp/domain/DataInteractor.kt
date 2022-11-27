package com.shevy.thetestapp.domain

import com.shevy.thetestapp.domain.model.basket.Basket
import com.shevy.thetestapp.domain.model.detail.Detail
import com.shevy.thetestapp.domain.model.products.Product
import kotlinx.coroutines.Deferred

interface DataInteractor {

    fun getCart(): Deferred<Basket>

    fun getDetail(): Deferred<Detail>

    fun getProducts(): Deferred<Product>
}