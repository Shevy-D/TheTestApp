package com.shevy.thetestapp.data

import com.shevy.thetestapp.data.model.products.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface GetProductsInterface {

    //https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175

    @GET("v3/654bd15e-b121-49ba-a588-960956b15175")
    fun getProducts() : Call<Product>

    companion object {

        var BASE_URL = "https://run.mocky.io/"

        fun create() : GetProductsInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GetProductsInterface::class.java)

        }
    }
}