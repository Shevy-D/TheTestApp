package com.shevy.thetestapp.presentation.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shevy.thetestapp.databinding.ActivityCartBinding
import com.shevy.thetestapp.domain.DataInteractor
import com.shevy.thetestapp.domain.model.basket.Basket
import com.shevy.thetestapp.presentation.adapterdelegation.CompositeDelegateAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartRecyclerView: RecyclerView
    private lateinit var compositeDelegateAdapter: CompositeDelegateAdapter
    private val interactor: DataInteractor by inject()

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

        compositeDelegateAdapter = CompositeDelegateAdapter(CartDelegateAdapter())
        cartRecyclerView.adapter = compositeDelegateAdapter

        lifecycleScope.launch {
            val cart = getCartResponse()

            compositeDelegateAdapter.swapData(cart.basket)

            initCartViewData(cart)
        }
    }

    private suspend fun getCartResponse(): Basket {
        return interactor.getCart().await()
    }

    private fun initCartViewData(basket: Basket?) {
        val price = "$${(basket?.total?.div(1000))},${(basket?.total?.rem(1000))} us"
        binding.totalPrice.text = price
        binding.deliveryText.text = basket?.delivery
    }
}