package com.shevy.thetestapp

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListAdapter
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.thetestapp.model.BestSeller


class BestSellerAdapter(private val bestSellerList: ArrayList<BestSeller>) :
    RecyclerView.Adapter<BestSellerAdapter.BestSellerHolder>() {
    class BestSellerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val bestSellerView: ImageView = itemView.findViewById(R.id.image_best_seller)
        val title: TextView = itemView.findViewById(R.id.title_best_seller)
        val discount: TextView = itemView.findViewById(R.id.price_with_discount)
        val price: TextView = itemView.findViewById(R.id.price_without_discount)
        val favorite: ImageView = itemView.findViewById(R.id.image_favorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerHolder {
        return BestSellerHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.best_seller_container, parent, false)
        )
    }

    override fun onBindViewHolder(holder: BestSellerHolder, position: Int) {
        val itemBestSeller = bestSellerList[position]


        holder.title.text = itemBestSeller.title
        holder.price.text = itemBestSeller.discount_price.toString()
        holder.discount.apply {
            this.text = itemBestSeller.price_without_discount.toString()
            holder.discount.paintFlags = this.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        }

        Glide.with(holder.bestSellerView.context)
            .load(itemBestSeller.picture)
            .into(holder.bestSellerView)

        if (itemBestSeller.is_favorites) {
            holder.favorite.setImageResource(R.drawable.ic_heart_filled)
        }
    }

    override fun getItemCount(): Int = 4 //bestSellerList.size
}