package com.bunk.urbanmobility.data.entity

import com.google.gson.annotations.SerializedName

data class RepositoryResponse(
    @SerializedName("total_count") val count: Int,
    val items: List<RepositoryItem>

)