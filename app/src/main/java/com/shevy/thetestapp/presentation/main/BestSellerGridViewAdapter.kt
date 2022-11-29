package com.shevy.thetestapp.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.shevy.thetestapp.R
import com.shevy.thetestapp.domain.model.products.BestSeller
import com.shevy.thetestapp.presentation.detail.DetailActivity


class BestSellerGridViewAdapter(
    private val context: Context
) :
    BaseAdapter() {

    private val bestSellerList = mutableListOf<BestSeller>()

    @SuppressLint("NotifyDataSetChanged")
    fun setProducts(newBestSellerList: List<BestSeller>) {
        bestSellerList.apply {
            clear()
            addAll(newBestSellerList)
        }
        notifyDataSetChanged()
    }

    override fun getCount(): Int = bestSellerList.size

    override fun getItem(position: Int): Any = bestSellerList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    @SuppressLint("ViewHolder", "SetTextI18n")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val itemBestSeller = bestSellerList[position]

        val view = LayoutInflater.from(context)
            .inflate(R.layout.best_seller_container, parent, false) as View

        val bestSellerView: ImageView = view.findViewById(R.id.image_best_seller)
        val title: TextView = view.findViewById(R.id.title_best_seller)
        val discount: TextView = view.findViewById(R.id.price_with_discount)
        val price: TextView = view.findViewById(R.id.price_without_discount)
        val favorite: ImageView = view.findViewById(R.id.image_favorite)

        title.text = itemBestSeller.title
        price.text = "$${itemBestSeller.discount_price}"
        discount.apply {
            this.text = "$${itemBestSeller.price_without_discount}"
            discount.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }
        Glide.with(bestSellerView.context)
            .load(itemBestSeller.picture)
            .into(bestSellerView)

        if (itemBestSeller.is_favorites) {
            favorite.setImageResource(R.drawable.ic_heart_filled)
        }

        val container: ConstraintLayout = view.findViewById(R.id.best_seller_container_parent)
        container.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            view.context.startActivity(intent)
        }
        return view
    }
}
