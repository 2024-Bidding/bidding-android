package com.seunghoon.bidding_android.feature.mypage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.seunghoon.bidding_android.common.insertIntoGlide
import com.seunghoon.bidding_android.databinding.ItemMyBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.navigation.navigateToItemDetails

class MyItemsAdapter(
    private val items: List<ItemsEntity.ItemEntity>,
    private val navController: NavController,
) :
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

            insertIntoGlide(
                context = holder.itemView.context,
                imageView = imgItemMy,
                url = item.imageUrl,
            )
        }

        holder.itemView.setOnClickListener {
            navController.navigateToItemDetails(itemId = items[position].id)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
