package com.shevy.thetestapp.presentation.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shevy.thetestapp.data.GetCartInterface
import com.shevy.thetestapp.data.model.basket.Basket
import com.shevy.thetestapp.databinding.ActivityCartBinding
import com.shevy.thetestapp.presentation.adapterdelegation.adapters.CartDelegateAdapter
import com.shevy.thetestapp.presentation.adapterdelegation.delegate.CompositeDelegateAdapterTest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding
    lateinit var cartRecyclerView: RecyclerView
    private lateinit var adapter: CartAdapter
    private lateinit var compositeDelegateAdapter: CompositeDelegateAdapterTest

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
                //adapter = CartAdapter(response.body()?.basket)
                //cartRecyclerView.adapter = adapter
                compositeDelegateAdapter = CompositeDelegateAdapterTest(CartDelegateAdapter())
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