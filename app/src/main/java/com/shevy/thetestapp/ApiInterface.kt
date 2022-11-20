package com.shevy.thetestapp

import com.shevy.thetestapp.model.HomeStore
import com.shevy.thetestapp.model.Product
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface ApiInterface {

    //https://run.mocky.io/v3/654bd15e-b121-49ba-a588-960956b15175

    @GET("v3/654bd15e-b121-49ba-a588-960956b15175")
    fun getProducts() : Call<Product>

    companion object {

        var BASE_URL = "https://run.mocky.io/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}