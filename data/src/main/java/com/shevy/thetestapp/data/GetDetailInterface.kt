package com.shevy.thetestapp.data

import com.shevy.thetestapp.data.model.detail.Detail
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface GetDetailInterface {

    @GET("v3/6c14c560-15c6-4248-b9d2-b4508df7d4f5")
    fun getDetail() : Call<Detail>

    companion object {

        var BASE_URL = "https://run.mocky.io/"

        fun create() : GetDetailInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(GetDetailInterface::class.java)
        }
    }
}