package com.shevy.thetestapp.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import com.shevy.thetestapp.R
import com.shevy.thetestapp.databinding.ActivityMainBinding
import com.shevy.thetestapp.domain.DataInteractor
import com.shevy.thetestapp.domain.model.basket.Basket
import com.shevy.thetestapp.domain.model.products.Product
import com.shevy.thetestapp.presentation.adapterdelegation.CompositeDelegateAdapter
import com.shevy.thetestapp.presentation.cart.CartActivity
import com.shevy.thetestapp.viewmodel.MainViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bestSellerGridViewAdapter: BestSellerGridViewAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var bestSellerGridView: GridView
    private lateinit var compositeDelegateAdapter: CompositeDelegateAdapter

    private val interactor: DataInteractor by inject()

    private val mainViewModel by viewModel<MainViewModel>()

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(500)
        setTheme(R.style.Theme_TheTestApp)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        showFilterBottomSheet()

        //testFunctionForSpinnerInBottomSheet()

        initAdapterHotSales()
        initGridViewBestSeller()

/*        binding.cartBottomNavigation.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }*/

        lifecycleScope.launch {
            val numbers = getCartResponse().basket.size
            val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.nav_cart)
            badge.isVisible = numbers > 0
            badge.number = numbers
        }

        val cartBtn = findViewById<BottomNavigationItemView>(R.id.nav_cart)
        cartBtn.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private suspend fun getCartResponse(): Basket {
        return interactor.getCart().await()
    }

/*    private fun testFunctionForSpinnerInBottomSheet() {
        val priceList = arrayListOf(
            "\$0 - \$500",
            "\$300 - \$500",
            "\$500 - \$1000",
            "\$1000 - \$10000"
        )

        val priceAdapter =
            ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, priceList)

        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val bindingBSh = LayoutBottomSheetBinding.inflate(layoutInflater)
        val spinnerPrice = bindingBSh.spinnerId
        spinnerPrice.adapter = priceAdapter

        spinnerPrice.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(baseContext, p0?.selectedItem.toString(), Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }*/

    private fun showFilterBottomSheet() {
        val bottomSheetButton = binding.showFilter

        bottomSheetButton.setOnClickListener {
            val bottomSheetDialog =
                BottomSheetDialog(this@MainActivity, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext)
                .inflate(R.layout.layout_bottom_sheet, findViewById(R.id.bottomSheetContainer))
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }

    private fun initGridViewBestSeller() {

        bestSellerGridView = binding.bestSellerGridview

        bestSellerGridViewAdapter = BestSellerGridViewAdapter(this@MainActivity)
        bestSellerGridView.adapter = bestSellerGridViewAdapter

        lifecycleScope.launch {
            val products = getProductsResponse()
            bestSellerGridViewAdapter.setProducts(products.best_seller)
        }
    }

    private suspend fun getProductsResponse(): Product {
        return interactor.getProducts().await()
    }

    private fun initAdapterHotSales() {

        viewPager = binding.viewPager

        compositeDelegateAdapter = CompositeDelegateAdapter(HotSalesDelegateAdapter())
        viewPager.adapter = compositeDelegateAdapter

        lifecycleScope.launch {
            val products = getProductsResponse()
            compositeDelegateAdapter.swapData(products.home_store)
        }

        viewPager.run {
            offscreenPageLimit = 3
            clipToPadding = false
            clipChildren = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }
    }
}
