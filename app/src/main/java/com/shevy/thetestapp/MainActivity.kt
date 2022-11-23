package com.shevy.thetestapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shevy.thetestapp.databinding.ActivityMainBinding
import com.shevy.thetestapp.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var hotSalesAdapter: HotSalesAdapter
    private lateinit var bestSellerGridViewAdapter: BestSellerGridViewAdapter
    private lateinit var viewPager: ViewPager2
    private lateinit var handler: Handler
    private lateinit var bestSellerGridView: GridView

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
/*                Log.d(
                    "testLogs",
                    "OnResponse success ${response.body()?.home_store?.get(0)?.title}"
                )*/

                bestSellerGridViewAdapter = BestSellerGridViewAdapter(
                    this@MainActivity,
                    response.body()?.best_seller ?: emptyList()
                )
                bestSellerGridView.adapter = bestSellerGridViewAdapter
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                //Log.d("testLogs", "OnResponse failure ${t.message}")
            }
        })
    }

    private fun initAdapterHotSales() {

        viewPager = binding.viewPager
        handler = Handler(Looper.myLooper()!!)

        val getProductsInterface = GetProductsInterface.create().getProducts()
        getProductsInterface.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
/*                Log.d(
                    "testLogs",
                    "OnResponse success ${response.body()?.home_store?.get(0)?.title}"
                )*/

                hotSalesAdapter =
                    HotSalesAdapter(response.body()?.home_store ?: emptyList(), viewPager)
                viewPager.adapter = hotSalesAdapter
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                //Log.d("testLogs", "OnResponse failure ${t.message}")
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