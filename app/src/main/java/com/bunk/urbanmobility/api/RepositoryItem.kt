package com.bunk.urbanmobility.api

import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    val id: Int,
    val name: String,
    @SerializedName("stargazers_count") val stars: Int
)