package com.shevy.thetestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.shevy.thetestapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        //setContentView(R.layout.activity_main)
    }
}