package com.seunghoon.bidding_android.feature.registeritem

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seunghoon.bidding_android.databinding.ItemRegisterImageBinding

class RegisterImageAdapter(private val images: MutableList<Uri>) :
    RecyclerView.Adapter<RegisterImageAdapter.RegisterImageViewHolder>() {

    inner class RegisterImageViewHolder(val binding: ItemRegisterImageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RegisterImageViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return RegisterImageViewHolder(ItemRegisterImageBinding.inflate(layoutInflater, parent, false))
    }

    override fun onBindViewHolder(holder: RegisterImageViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(images[position])
            .into(holder.binding.imgRegisterImage)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    internal fun addImage(uri: Uri) {
        images.add(uri)
    }
}
