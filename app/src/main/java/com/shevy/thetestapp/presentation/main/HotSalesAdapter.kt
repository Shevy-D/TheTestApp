package com.shevy.thetestapp.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.shevy.thetestapp.R
import com.shevy.thetestapp.data.model.products.HomeStore

class HotSalesAdapter(
    private val product: List<HomeStore>
) :
    RecyclerView.Adapter<HotSalesAdapter.HotSalesViewHolder>() {

    class HotSalesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val productView: ImageView = itemView.findViewById(R.id.image_hot_sale)
        val title: TextView = itemView.findViewById(R.id.text_title)
        val description: TextView = itemView.findViewById(R.id.text_description)
        val new: RelativeLayout = itemView.findViewById(R.id.box_new)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSalesViewHolder {
        return HotSalesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.hot_sales_container, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HotSalesViewHolder, position: Int) {
        val itemProducts = product[position]

        holder.title.text = itemProducts.title
        holder.description.text = itemProducts.subtitle
        Glide.with(holder.productView.context)
            .load(itemProducts.picture)
            .into(holder.productView)

        if (!itemProducts.is_new) {
            holder.new.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int = product.size
}