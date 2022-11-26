package com.shevy.thetestapp.presentation.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shevy.thetestapp.data.GetCartInterface
import com.shevy.thetestapp.data.model.basket.Basket
import com.shevy.thetestapp.databinding.ActivityCartBinding
import com.shevy.thetestapp.presentation.adapterdelegation.CompositeDelegateAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var compositeDelegateAdapter: CompositeDelegateAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initCartAdapter()

        binding.backBtnCart.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initCartAdapter() {

        cartRecyclerView = binding.cartRV
        cartRecyclerView.layoutManager = LinearLayoutManager(this)

        val getCartInterface = GetCartInterface.create().getCartItems()
        getCartInterface.enqueue(object : Callback<Basket> {
            override fun onResponse(call: Call<Basket>, response: Response<Basket>) {
                compositeDelegateAdapter = CompositeDelegateAdapter(CartDelegateAdapter())
                cartRecyclerView.adapter = compositeDelegateAdapter

                response.body()?.basket?.let { compositeDelegateAdapter.swapData(it) }

                initCartViewData(response.body())
            }

            override fun onFailure(call: Call<Basket>, t: Throwable) {
            }
        })
    }

    private fun initCartViewData(basket: Basket?) {
        val price = "$${(basket?.total?.div(1000))},${(basket?.total?.rem(1000))} us"
        binding.totalPrice.text = price
        binding.deliveryText.text = basket?.delivery
    }
}