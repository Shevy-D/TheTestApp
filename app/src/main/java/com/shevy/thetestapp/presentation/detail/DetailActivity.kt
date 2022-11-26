package com.shevy.thetestapp.presentation.detail

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
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
    private lateinit var adapter: DetailAdapter

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
                adapter =
                    DetailAdapter(response.body())
                viewPager.adapter = adapter

                initDetailViewData(response.body()!!)
            }

            override fun onFailure(call: Call<Detail>, t: Throwable) {
            }
        })

        val transformer = CompositePageTransformer()
        transformer.run {
            addTransformer(MarginPageTransformer(40))
            addTransformer { page, position ->
                val r = 1 - abs(position)
                page.scaleY = 0.85f + r * 0.14f
            }
        }

        viewPager.run {
            offscreenPageLimit = 3
            clipChildren = false
            clipToPadding = false
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(transformer)
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
}