package com.shevy.thetestapp.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shevy.thetestapp.data.GetProductsInterface
import com.shevy.thetestapp.R
import com.shevy.thetestapp.databinding.ActivityMainBinding
import com.shevy.thetestapp.data.model.products.Product
import com.shevy.thetestapp.presentation.adapterdelegation.CompositeDelegateAdapter
import com.shevy.thetestapp.presentation.cart.CartActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var bestSellerGridViewAdapter: BestSellerGridViewAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var bestSellerGridView: GridView
    private lateinit var compositeDelegateAdapter: CompositeDelegateAdapter

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(500)
        setTheme(R.style.Theme_TheTestApp)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        showFilterBottomSheet()

        //testFunctionForSpinnerInBottomSheet()

        initAdapterHotSales()
        initRecyclerViewBestSeller()

        binding.cartBottomNavigation.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }
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

    private fun initRecyclerViewBestSeller() {

        bestSellerGridView = binding.bestSellerGridview

        val getProductsInterface = GetProductsInterface.create().getProducts()
        getProductsInterface.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                bestSellerGridViewAdapter = BestSellerGridViewAdapter(
                    this@MainActivity,
                    response.body()?.best_seller ?: emptyList()
                )
                bestSellerGridView.adapter = bestSellerGridViewAdapter
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {}
        })
    }

    private fun initAdapterHotSales() {

        viewPager = binding.viewPager

        val getProductsInterface = GetProductsInterface.create().getProducts()
        getProductsInterface.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                compositeDelegateAdapter = CompositeDelegateAdapter(HotSalesDelegateAdapter())
                viewPager.adapter = compositeDelegateAdapter

                compositeDelegateAdapter.swapData(response.body()?.home_store ?: emptyList())
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
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
