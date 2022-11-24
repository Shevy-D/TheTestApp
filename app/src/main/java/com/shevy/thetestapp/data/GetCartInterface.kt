package com.shevy.thetestapp.data

import com.shevy.thetestapp.data.model.basket.Basket
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface GetCartInterface {

    //https://run.mocky.io/v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149

    @GET("v3/53539a72-3c5f-4f30-bbb1-6ca10d42c149")
    fun getCartItems() : Call<Basket>

    companion object {

        var BASE_URL = "https://run.mocky.io/"

        fun create() : GetCartInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GetCartInterface::class.java)
        }
    }
}