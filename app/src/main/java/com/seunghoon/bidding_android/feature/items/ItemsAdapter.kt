package com.seunghoon.bidding_android.feature.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunghoon.bidding_android.databinding.ItemBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity

internal class ItemsAdapter(
    private val items: MutableList<ItemsEntity.ItemEntity>
) : RecyclerView.Adapter<ItemsAdapter.ItemsViewHolder>() {
    class ItemsViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ItemsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ItemsViewHolder(ItemBinding.inflate(layoutInflater, parent, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemsViewHolder, position: Int) {
        with(holder.binding) {
            item = items[position].copy(imageUrl = "https://jobis-store.s3.ap-northeast-2.amazonaws.com/" + items[position].imageUrl)
            Glide.with(holder.itemView.context).load(items[position].imageUrl).into(imgItem)
        }
    }

    fun clearItems() {
        items.clear()
    }

    fun addItems(items: List<ItemsEntity.ItemEntity>) {
        this.items.addAll(items)
    }
}
