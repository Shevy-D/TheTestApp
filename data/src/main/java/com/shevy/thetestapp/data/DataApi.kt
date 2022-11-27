package com.shevy.thetestapp.data

import com.shevy.thetestapp.domain.model.basket.Basket
import com.shevy.thetestapp.domain.model.detail.Detail
import com.shevy.thetestapp.domain.model.products.Product
import retrofit2.http.GET

interface DataApi {

    @GET("v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    suspend fun getCartItems(): Basket

    @GET("v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    suspend fun getDetail() : Detail

    @GET("v3/654bd15e-b121-49ba-a588-960956b15175")
    suspend fun getProducts() : Product

    companion object {
        var BASE_URL = "https://run.mocky.io/"
    }
}