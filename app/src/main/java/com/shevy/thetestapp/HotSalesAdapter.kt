package com.shevy.thetestapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.shevy.thetestapp.model.HomeStore

class HotSalesAdapter(
    private val product: List<HomeStore>,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<HotSalesAdapter.HotSalesViewHolder>() {

    class HotSalesViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        //private val binding = Recyclerview
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

        if (!itemProducts.is_new){
            holder.new.visibility = View.GONE
        }
/*        if (position == product.size - 1) {
            viewPager2.post(runnable)
        }*/
    }

    override fun getItemCount(): Int = product.size

/*    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        product.addAll(product)
        notifyDataSetChanged()
    }*/
}