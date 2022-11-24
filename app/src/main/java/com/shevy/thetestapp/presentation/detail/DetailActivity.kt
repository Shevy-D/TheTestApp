package com.shevy.thetestapp.presentation.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.shevy.thetestapp.R
import com.shevy.thetestapp.data.GetDetailInterface
import com.shevy.thetestapp.data.model.detail.Detail
import com.shevy.thetestapp.databinding.ActivityDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.abs

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: DetailsAdapter
    //private val sliderHandler: Handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater).also { setContentView(it.root) }

        initAdapterProductDetails()
    }

    private fun initAdapterProductDetails() {

        viewPager = binding.viewPagerProductDetails

        val getDetailInterface = GetDetailInterface.create().getDetail()
        getDetailInterface.enqueue(object : Callback<Detail> {
            override fun onResponse(
                call: Call<Detail>,
                response: Response<Detail>
            ) {
                Log.d(
                    "testLogs",
                    "OnResponse success ${response.body()}"
                )

                adapter =
                    DetailsAdapter(response.body(), viewPager)
                viewPager.adapter = adapter

                initDetailViewData(response.body()!!)
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
                Log.d("testLogs", "OnResponse failure ${t.message}")
            }
        })

        val transformer = CompositePageTransformer()
        transformer.run {
            addTransformer(MarginPageTransformer(40))
            addTransformer(object : ViewPager2.PageTransformer {
                override fun transformPage(page: View, position: Float) {
                    val r = 1 - abs(position)
                    page.scaleY = 0.85f + r * 0.14f
                }
            })
        }

        viewPager.run {
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
/*            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    sliderHandler.removeCallbacks(sliderRunnable)
                    sliderHandler.postDelayed(sliderRunnable, 2000)
                }
            })*/
        }
    }

    @SuppressLint("SetTextI18n", "UseCompatLoadingForDrawables")
    private fun initDetailViewData(responseDetail: Detail) {

        binding.titleDetailItem.text = responseDetail.title
        binding.textCpu.text = responseDetail.CPU
        binding.textCamera.text = responseDetail.camera
        binding.textSsd.text = responseDetail.ssd
        binding.textSd.text = responseDetail.sd

        binding.ratingBar.rating = responseDetail.rating.toFloat()

        val backgroundOne = GradientDrawable().also {
            it.setColor(Color.parseColor(responseDetail.color[0]))
            it.cornerRadius = 100F
        }
        binding.colorOne.background = backgroundOne

        val backgroundTwo = GradientDrawable().also {
            it.setColor(Color.parseColor(responseDetail.color[1]))
            it.cornerRadius = 100F
        }
        binding.colorTwo.background = backgroundTwo

        binding.capacityOne.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.capacityOne.text = "${responseDetail.capacity[0]} GB"
            } else {
                binding.capacityOne.text = "${responseDetail.capacity[0]} gb"
            }
        }

        binding.capacityTwo.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.capacityTwo.text = "${responseDetail.capacity[1]} GB"
            } else {
                binding.capacityTwo.text = "${responseDetail.capacity[1]} gb"
            }
        }

        if (responseDetail.isFavorites) {
            binding.favoriteBtnDetail.background =
                getDrawable(R.drawable.ic_favorites_btn_for_detail_product_checked)
        }

        val price = "$${(responseDetail.price / 1000)},${(responseDetail.price % 1000)}.00"
        binding.priceAddToCart.text = price
    }

/*    private val sliderRunnable: Runnable = Runnable() {
        run() {
            viewPager.setCurrentItem(viewPager.currentItem + 1)
        }
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 2000)
    }*/
}