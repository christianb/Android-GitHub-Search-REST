package com.bunk.urbanmobility.view.list

import com.bunk.urbanmobility.data.entity.RepositoryItem

interface RepositoryItemClickListener {
    fun onItemClick(repositoryItem: RepositoryItem)
}