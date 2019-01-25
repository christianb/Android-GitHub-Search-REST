package com.bunk.github.view.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bunk.github.R
import com.bunk.github.data.entity.RepositoryItem
import kotlinx.android.synthetic.main.repository_viewholder.view.*

private val TAG = RepositoryViewHolder::class.java.simpleName

class RepositoryViewHolder(
    parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.repository_viewholder, parent, false)
) {
    fun bindData(item: RepositoryItem, repositoryItemClickListener: RepositoryItemClickListener) {
        itemView.nameTextView.text = item.name
        itemView.starsTextView.text = String.format(itemView.context.getString(R.string.stars), item.stars)
        itemView.languageTextView.text = String.format(itemView.context.getString(R.string.language), item.language)

        itemView.setOnClickListener {
            repositoryItemClickListener.onItemClick(item)
        }
    }
}