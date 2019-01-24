package com.bunk.urbanmobility.view.list

import com.bunk.urbanmobility.api.entity.RepositoryItem

interface RepositoryItemClickListener {
    fun onItemClick(repositoryItem: RepositoryItem)
}