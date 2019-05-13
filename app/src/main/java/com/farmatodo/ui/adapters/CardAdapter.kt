package com.farmatodo.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.farmatodo.R
import com.farmatodo.data.BaseData
import com.farmatodo.data.Character
import com.farmatodo.data.Creator
import com.farmatodo.ui.holders.CardHolder
import com.farmatodo.utils.OnAdapterItemClick

class CardAdapter (private var items:MutableList<BaseData>, private val context:Context, private val onItemClickListener: OnAdapterItemClick?) : RecyclerView.Adapter<CardHolder>() {

    companion object{
        const val IMAGE_NOT_AVAILABLE = "image_not_available"
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CardHolder {
        return CardHolder(LayoutInflater.from(context).inflate(R.layout.list_item_card, viewGroup, false))
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CardHolder, position: Int) {
        val item = items[position]
        when (item) {
            is Creator -> {
                item.fullName?.let {
                    holder.tvTitle.text = it
                }
            }
            is Character -> item.name?.let {
                holder.tvTitle.text = it
            }
            else -> item.title?.let {
                holder.tvTitle.text = it
            }
        }

        if(item.thumbnail != null && !item.thumbnail.path.contains(IMAGE_NOT_AVAILABLE)){
            Glide
                .with(context)
                .load(item.thumbnail.path + "." +item.thumbnail.extension)
                .centerCrop()
                .into(holder.ivThumbnail)
        } else {
            Glide
                .with(context)
                .load(R.drawable.marvel_default_image)
                .centerInside()
                .into(holder.ivThumbnail)
        }
        onItemClickListener?.let {
            holder.itemView.setOnClickListener { onItemClickListener.onItemClick(item) }
        }
    }
}