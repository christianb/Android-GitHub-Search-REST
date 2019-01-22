package com.bunk.urbanmobility.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bunk.urbanmobility.R
import com.bunk.urbanmobility.api.entity.RepositoryItem
import kotlinx.android.synthetic.main.repository_viewholder.view.*

class RepositoryViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.repository_viewholder, parent, false)
) {
    fun bindData(item: RepositoryItem) {
        itemView.nameTextView.text = String.format(itemView.context.getString(R.string.name), item.name)
    }
}