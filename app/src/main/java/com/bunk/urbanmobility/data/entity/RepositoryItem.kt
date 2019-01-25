package com.bunk.urbanmobility.data.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class RepositoryItem(
    val id: Int,
    val name: String,
    @SerializedName("full_name") val fullName: String,
    @SerializedName("stargazers_count") val stars: Int,
    val owner: Owner,
    @SerializedName("html_url") val url: String,
    val description: String,
    val language: String,
    @SerializedName("forks_count") val forks: Int,
    @SerializedName("open_issues_count") val openIssues: Int,
    @SerializedName("default_branch") val defaultBranch: String
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readInt(),
        source.readString() ?: "",
        source.readString() ?: "",
        source.readInt(),
        source.readParcelable<Owner>(Owner::class.java.classLoader) ?: Owner(""),
        source.readString() ?: "",
        source.readString() ?: "",
        source.readString() ?: "",
        source.readInt(),
        source.readInt(),
        source.readString() ?: ""
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(id)
        writeString(name)
        writeString(fullName)
        writeInt(stars)
        writeParcelable(owner, 0)
        writeString(url)
        writeString(description)
        writeString(language)
        writeInt(forks)
        writeInt(openIssues)
        writeString(defaultBranch)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RepositoryItem> = object : Parcelable.Creator<RepositoryItem> {
            override fun createFromParcel(source: Parcel): RepositoryItem = RepositoryItem(source)
            override fun newArray(size: Int): Array<RepositoryItem?> = arrayOfNulls(size)
        }
    }
}