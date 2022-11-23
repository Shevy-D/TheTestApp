package com.shevy.thetestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shevy.thetestapp.databinding.ActivityDetailsBinding
import com.shevy.thetestapp.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var testAdapter: ProductDetailsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initAdapterProductDetails()
    }

    private fun initAdapterProductDetails() {

        viewPager = binding.viewPagerProductDetails
        handler = Handler(Looper.myLooper()!!)

        val getProductsInterface = GetProductsInterface.create().getProducts()
        getProductsInterface.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                Log.d(
                    "testLogs",
                    "OnResponse success ${response.body()?.home_store?.get(0)?.title}"
                )

                testAdapter =
                    ProductDetailsAdapter(response.body()?.home_store ?: emptyList(), viewPager)
                viewPager.adapter = testAdapter
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.d("testLogs", "OnResponse failure ${t.message}")
            }
        })

        viewPager.run {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}