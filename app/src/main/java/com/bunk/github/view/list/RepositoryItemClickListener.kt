package com.bunk.github.view.list

import com.bunk.github.data.entity.RepositoryItem

interface RepositoryItemClickListener {
    fun onItemClick(repositoryItem: RepositoryItem)
}