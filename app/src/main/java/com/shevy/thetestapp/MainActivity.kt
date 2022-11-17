package com.shevy.thetestapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide.init
import com.shevy.thetestapp.databinding.ActivityMainBinding
import com.shevy.thetestapp.model.HomeStore


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var adapter: HotSalesAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var handler : Handler
    private lateinit var product: ArrayList<HomeStore>

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000)
        setTheme(R.style.Theme_TheTestApp)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initAll()
    }

    private fun initAll() {
        viewPager = binding.viewPager
        handler = Handler(Looper.myLooper()!!)
        product = ArrayList()

        product.add(HomeStore(1,
            is_buy = true,
            is_new = true,
            picture = "https://img.ibxk.com.br/2020/09/23/23104013057475.jpg?w=1120&h=420&mode=crop&scale=both",
            subtitle = "Súper. Mega. Rápido.",
            title = "Iphone 12"
        ))

        product.add(HomeStore(2,
            is_buy = true,
            is_new = false,
            picture = "https://cdn-2.tstatic.net/kupang/foto/bank/images/pre-order-samsung-galaxy-a71-laris-manis-inilah-rekomendasi-ponsel-harga-rp-6-jutaan.jpg",
            subtitle = "Súper. Mega. Rápido.",
            title = "Samsung Galaxy A71"
        ))

        product.add(HomeStore(3,
            is_buy = true,
            is_new = false,
            picture = "https://static.digit.in/default/942998b8b4d3554a6259aeb1a0124384dbe0d4d5.jpeg",
            subtitle = "Súper. Mega. Rápido.",
            title = "Xiaomi Mi 11 ultra"
        ))

        adapter = HotSalesAdapter(product, viewPager)


        viewPager.adapter = adapter
        viewPager.run {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}