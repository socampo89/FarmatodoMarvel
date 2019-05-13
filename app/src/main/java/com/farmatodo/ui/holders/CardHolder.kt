package com.farmatodo.ui.holders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_card.view.*

class CardHolder (view: View) : RecyclerView.ViewHolder(view) {
    val ivThumbnail = view.ivThumbnail
    val tvTitle = view.tvTitle
}