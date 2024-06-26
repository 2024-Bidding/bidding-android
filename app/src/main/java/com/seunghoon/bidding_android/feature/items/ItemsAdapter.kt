package com.seunghoon.bidding_android.feature.items

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.seunghoon.bidding_android.R
import com.seunghoon.bidding_android.common.insertIntoGlide
import com.seunghoon.bidding_android.databinding.ItemBinding
import com.seunghoon.bidding_android.domain.entity.items.ItemsEntity
import com.seunghoon.bidding_android.navigation.navigateToItemDetails

internal class ItemsAdapter(
    private val items: MutableList<ItemsEntity.ItemEntity>,
    private val navController: NavController,
    private val viewModel: ItemsViewModel,
    private val isLikeAdapter: Boolean,
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
            imgItemLike.setLikeImage(items[position].isLiked)
            imgItemLike.setOnClickListener {
                items[position] = items[position].copy(isLiked = !items[position].isLiked)
                Log.d("TEST", items[position].isLiked.toString())

                viewModel.likeItem(items[position].id)

                imgItemLike.setLikeImage(items[position].isLiked)

                if (!items[position].isLiked && isLikeAdapter) {
                    notifyItemRemoved(position)
                    items.remove(items[position])
                }
            }
        }
        holder.itemView.setOnClickListener {
            navController.navigateToItemDetails(itemId = items[position].id)
        }
    }

    private fun ImageView.setLikeImage(isLiked: Boolean) {
        setImageResource(
            when (isLiked) {
                true -> R.drawable.ic_like
                false -> R.drawable.ic_like_off
            }
        )
    }

    fun clearItems() {
        items.clear()
    }

    fun addItems(items: List<ItemsEntity.ItemEntity>) {
        this.items.addAll(items)
    }
}
