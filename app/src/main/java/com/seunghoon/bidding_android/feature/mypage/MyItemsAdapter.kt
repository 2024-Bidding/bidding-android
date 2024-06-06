package com.seunghoon.bidding_android.feature.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunghoon.bidding_android.common.getFullImageUrl
import com.seunghoon.bidding_android.databinding.ItemMyBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity

class MyItemsAdapter(private val items: List<ItemsEntity.ItemEntity>) :
    RecyclerView.Adapter<MyItemsAdapter.MyItemsViewHolder>() {

    class MyItemsViewHolder(val binding: ItemMyBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyItemsViewHolder {
        val binding = ItemMyBinding.inflate(LayoutInflater.from(parent.context))
        return MyItemsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyItemsViewHolder, position: Int) {
        val item = items[position]
        with(holder.binding) {
            tvItemMyTitle.text = item.name
            tvPrice.text = item.currentPrice

            Glide.with(holder.itemView.context).load(getFullImageUrl(item.imageUrl)).into(imgItemMy)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
