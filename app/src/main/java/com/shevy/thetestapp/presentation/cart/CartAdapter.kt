package com.shevy.thetestapp.presentation.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.thetestapp.R
import com.shevy.thetestapp.data.model.basket.BasketX

class CartAdapter(
    private val cartItems: List<BasketX>?
) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val imageCart: ImageView = itemView.findViewById(R.id.image_cart)
        val titleCart: TextView = itemView.findViewById(R.id.title_cart)
        val priceCart: TextView = itemView.findViewById(R.id.price_cart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.cart_container, parent, false)
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val itemCart = cartItems?.get(position)

        holder.titleCart.text = itemCart?.title
        holder.priceCart.text = "$${itemCart?.price}.00"
        Glide.with(holder.imageCart.context)
            .load(itemCart?.images)
            .into(holder.imageCart)
    }

    override fun getItemCount(): Int = cartItems?.size ?: 0

}