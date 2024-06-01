package com.seunghoon.bidding_android.feature.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunghoon.bidding_android.databinding.ItemDetailsImageBinding

internal class ItemImageAdapter(val images: List<String>) :
    RecyclerView.Adapter<ItemImageAdapter.ItemImageViewHolder>() {

    class ItemImageViewHolder(val binding: ItemDetailsImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemImageViewHolder {
        val binding =
            ItemDetailsImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemImageViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: ItemImageViewHolder, position: Int) {
        val imageView = holder.binding.imgItemDetails
        Glide.with(holder.itemView.context)
            .load("https://jobis-store.s3.ap-northeast-2.amazonaws.com/" + images[position])
            .into(imageView)
    }


    override fun getItemCount(): Int {
        return images.size
    }
}
