package com.shevy.thetestapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.shevy.thetestapp.databinding.ActivityMainBinding
import com.shevy.thetestapp.model.BestSeller
import com.shevy.thetestapp.model.HomeStore
import com.shevy.thetestapp.notuseyet.BestSellerAdapter


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hotSalesAdapter: HotSalesAdapter
    private lateinit var bestSellerAdapter: BestSellerAdapter
    private lateinit var bestSellerGridViewAdapter: BestSellerGridViewAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var product: ArrayList<HomeStore>
    private lateinit var bestSellerGridView: GridView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000)
        setTheme(R.style.Theme_TheTestApp)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initAdapterHotSales()
        initRecyclerViewBestSeller()
    }

    private fun initRecyclerViewBestSeller() {

        bestSellerGridView = binding.bestSellerRv
        val bestSellerList = ArrayList<BestSeller>()

        bestSellerList.add(
            BestSeller(
                1500,
                111,
                true,
                "https://shop.gadgetufa.ru/images/upload/52534-smartfon-samsung-galaxy-s20-ultra-12-128-chernyj_1024.jpg",
                1047,
                "Samsung Galaxy s20 Ultra"
            )
        )
        bestSellerList.add(
            BestSeller(
                400,
                222,
                true,
                "https://mi92.ru/wp-content/uploads/2020/03/smartfon-xiaomi-mi-10-pro-12-256gb-global-version-starry-blue-sinij-1.jpg",
                300,
                "Xiaomi Mi 10 Pro"
            )
        )
        bestSellerList.add(
            BestSeller(
                1500,
                333,
                false,
                "https://opt-1739925.ssl.1c-bitrix-cdn.ru/upload/iblock/c01/c014d088c28d45b606ed8c58e5817172.jpg?160405904823488",
                1047,
                "Samsung Note 20 Ultra"
            )
        )
        bestSellerList.add(
            BestSeller(
                400,
                4444,
                true,
                "https://www.benchmark.rs/assets/img/news/edge1.jpg",
                300,
                "Motorola One Edge "
            )
        )

        bestSellerGridViewAdapter = BestSellerGridViewAdapter(this@MainActivity, bestSellerList)
        bestSellerGridView.adapter = bestSellerGridViewAdapter
    }

    private fun initAdapterHotSales() {
        viewPager = binding.viewPager
        handler = Handler(Looper.myLooper()!!)
        product = ArrayList()

        product.add(
            HomeStore(
                1,
                is_buy = true,
                is_new = true,
                picture = "https://img.ibxk.com.br/2020/09/23/23104013057475.jpg?w=1120&h=420&mode=crop&scale=both",
                subtitle = "Súper. Mega. Rápido.",
                title = "Iphone 12"
            )
        )

        product.add(
            HomeStore(
                2,
                is_buy = true,
                is_new = false,
                picture = "https://cdn-2.tstatic.net/kupang/foto/bank/images/pre-order-samsung-galaxy-a71-laris-manis-inilah-rekomendasi-ponsel-harga-rp-6-jutaan.jpg",
                subtitle = "Súper. Mega. Rápido.",
                title = "Samsung Galaxy A71"
            )
        )

        product.add(
            HomeStore(
                3,
                is_buy = true,
                is_new = false,
                picture = "https://static.digit.in/default/942998b8b4d3554a6259aeb1a0124384dbe0d4d5.jpeg",
                subtitle = "Súper. Mega. Rápido.",
                title = "Xiaomi Mi 11 ultra"
            )
        )

        hotSalesAdapter = HotSalesAdapter(product, viewPager)


        viewPager.adapter = hotSalesAdapter
        viewPager.run {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}