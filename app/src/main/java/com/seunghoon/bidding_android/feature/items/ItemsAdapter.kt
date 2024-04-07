package com.seunghoon.bidding_android.feature.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.seunghoon.bidding_android.databinding.ItemBinding

internal class ItemsAdapter(
    private val items: List<Item>
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
        holder.binding.item = items[position]
    }
}

data class Item(
    val id: Long,
    val name: String,
    val currentPrice: Long,
    val imageUrl: String,
    val biddingStatus: BiddingStatus,
    val endDate: String,
)

enum class BiddingStatus {
    BEFORE_BIDDING, // 입찰 전
    IN_BIDDING, // 입찰 중
    AFTER_BIDDING, // 입찰 완료
}
