package com.shevy.thetestapp.presentation.cart

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shevy.thetestapp.databinding.ActivityCartBinding

class CartActivity : AppCompatActivity() {

    lateinit var binding: ActivityCartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }
}