package com.shevy.thetestapp.presentation.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.shevy.thetestapp.R
import com.shevy.thetestapp.data.model.detail.Detail

class DetailsAdapter(
    private val detailsItem: Detail?,
    private val viewPager2: ViewPager2
) :
    RecyclerView.Adapter<DetailsAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        return DetailViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.product_details_container, parent, false)
        )
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {

        Glide.with(holder.detailView)
            .load(detailsItem?.images?.get(position))
            .into(holder.detailView)

/*        if (position == product.size - 1) {
            viewPager2.post(runnable)
        }*/
    }

    override fun getItemCount(): Int = detailsItem?.images?.size ?: 0

/*    @SuppressLint("NotifyDataSetChanged")
    private val runnable = Runnable {
        product.addAll(product)
        notifyDataSetChanged()
    }*/

    class DetailViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val detailView: ImageView = itemView.findViewById(R.id.image_details)
    }
}