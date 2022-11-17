package com.shevy.thetestapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.shevy.thetestapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private lateinit var adapter: HotSalesAdapter
    private lateinit var viewPager: ViewPager2

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {

        Thread.sleep(1000)
        setTheme(R.style.Theme_TheTestApp)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        adapter = HotSalesAdapter(this)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = adapter
    }
}