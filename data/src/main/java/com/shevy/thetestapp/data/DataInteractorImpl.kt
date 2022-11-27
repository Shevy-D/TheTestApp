package com.shevy.thetestapp.data

import com.shevy.thetestapp.domain.model.detail.Detail
import com.shevy.thetestapp.domain.model.products.Product
import com.shevy.thetestapp.domain.DataInteractor
import com.shevy.thetestapp.domain.model.basket.Basket
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class DataInteractorImpl(private val api: DataApi) : DataInteractor {

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun getCart(): Deferred<Basket> = scope.async {
        return@async api.getCartItems()
    }

    override fun getDetail(): Deferred<Detail> = scope.async {
        return@async api.getDetail()
    }

    override fun getProducts(): Deferred<Product> = scope.async {
        return@async api.getProducts()
    }
}