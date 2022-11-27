package com.shevy.thetestapp.presentation.adapterdelegation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface DelegateAdapter {
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, items: List<Any>, position: Int)
    fun onRecycled(holder: RecyclerView.ViewHolder)

    /** to know that current adapter can work with item at position */
    fun isForViewType(items: List<Any>, position: Int): Boolean

    /** [DiffUtilCallback] uses this to know that two items are the same */
    fun itemId(item: Any): Any
    /** [DiffUtilCallback] uses this to know that two items has the same content */
    fun itemContent(item: Any): Any

    fun onAttachedToWindow(holder: RecyclerView.ViewHolder)

    fun onDetachedFromWindow(holder: RecyclerView.ViewHolder)
}