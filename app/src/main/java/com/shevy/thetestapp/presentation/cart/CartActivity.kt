package com.shevy.thetestapp.presentation.cart

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shevy.thetestapp.data.GetCartInterface
import com.shevy.thetestapp.data.model.basket.Basket
import com.shevy.thetestapp.databinding.ActivityCartBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    lateinit var cartRecyclerView: RecyclerView
    private lateinit var adapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initCartAdapter()

    }

    private fun initCartAdapter() {

        cartRecyclerView = binding.cartRV
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        val getCartInterface = GetCartInterface.create().getCartItems()
        getCartInterface.enqueue(object : Callback<Basket> {
            override fun onResponse(call: Call<Basket>, response: Response<Basket>) {
                Log.d(
                    "testLogs",
                    "OnResponse success ${response.body()?.basket?.get(0)?.title}"
                )

                adapter = CartAdapter(response.body()?.basket)
                cartRecyclerView.adapter = adapter

                initCartViewData(response.body())
            }

            override fun onFailure(call: Call<Basket>, t: Throwable) {
                Log.d("testLogs", "OnResponse failure ${t.message}")
            }
        })
    }

    private fun initCartViewData(basket: Basket?) {

        val price = "$${(basket?.total?.div(1000))},${(basket?.total?.rem(1000))} us"
        binding.totalPrice.text = price
        binding.deliveryText.text = basket?.delivery

    }
}