package com.seunghoon.bidding_android.feature.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.seunghoon.bidding_android.common.insertIntoGlide
import com.seunghoon.bidding_android.databinding.ItemBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.navigation.navigateToItemDetails

internal class ItemsAdapter(
    private val items: MutableList<ItemsEntity.ItemEntity>,
    private val navController: NavController,
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
            val context = holder.itemView.context
            item = items[position]
            insertIntoGlide(
                context = context,
                imageView = imgItem,
                url = items[position].imageUrl,
            )
            insertIntoGlide(
                context = context,
                imageView = imgItemUserProfile,
                url = items[position].userProfileImageUrl,
            )
        }
        holder.itemView.setOnClickListener {
            navController.navigateToItemDetails(itemId = items[position].id)
        }
    }

    fun clearItems() {
        items.clear()
    }

    fun addItems(items: List<ItemsEntity.ItemEntity>) {
        this.items.addAll(items)
    }
}
