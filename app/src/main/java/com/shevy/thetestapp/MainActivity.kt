package com.shevy.thetestapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.shevy.thetestapp.databinding.ActivityMainBinding
import com.shevy.thetestapp.databinding.LayoutBottomSheetBinding
import com.shevy.thetestapp.model.BestSeller
import com.shevy.thetestapp.model.HomeStore
import com.shevy.thetestapp.model.Product
import com.shevy.thetestapp.notuseyet.BestSellerAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


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

        showFilterBottomSheet()

        testFunctionForSpinnerInBottomSheet()

        initAdapterHotSales()
        initRecyclerViewBestSeller()
    }

    private fun testFunctionForSpinnerInBottomSheet() {
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
    }

    private fun showFilterBottomSheet() {
        val bottomSheetButton = binding.showFilter

        bottomSheetButton.setOnClickListener {

            val bottomSheetDialog =
                BottomSheetDialog(this@MainActivity, R.style.BottomSheetDialogTheme)
            val bottomSheetView = LayoutInflater.from(applicationContext)
                .inflate(R.layout.layout_bottom_sheet, findViewById(R.id.bottomSheetContainer))
/*             bottomSheetView.findViewById<Button>(R.id.buttonShare).setOnClickListener {
                Toast.makeText(this@MainActivity, "Share...", Toast.LENGTH_SHORT).show()
                bottomSheetDialog.dismiss()
            }*/
            bottomSheetDialog.setContentView(bottomSheetView)
            bottomSheetDialog.show()
        }
    }

    private fun initRecyclerViewBestSeller() {

        bestSellerGridView = binding.bestSellerGridview
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

        val apiInterface = ApiInterface.create().getProducts()

        apiInterface.enqueue(object : Callback<Product> {
            override fun onResponse(
                call: Call<Product>,
                response: Response<Product>
            ) {
                Log.d(
                    "testLogs",
                    "OnResponse success ${response.body()?.home_store?.get(0)?.title}"
                )

                hotSalesAdapter =
                    HotSalesAdapter(response.body()?.home_store ?: emptyList(), viewPager)
                viewPager.adapter = hotSalesAdapter
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