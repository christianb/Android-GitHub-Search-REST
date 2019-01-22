package com.bunk.urbanmobility.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.bunk.urbanmobility.api.entity.RepositoryItem

class RepositoryAdapter : ListAdapter<RepositoryItem, RepositoryViewHolder>(DIFF_UTIL_ITEM_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bindData(getItem(position))
    }

    companion object {
        private val DIFF_UTIL_ITEM_CALLBACK =
            object : DiffUtil.ItemCallback<RepositoryItem>() {
                override fun areItemsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: RepositoryItem, newItem: RepositoryItem) =
                    oldItem == newItem
            }
    }
}