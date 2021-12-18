package com.letrinthehai.githubclient.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class User (
    @PrimaryKey(autoGenerate = false) @SerializedName("id") val id: Long,
    val login: String,
    @SerializedName("avatar_url") val avatarUrl: String,
    var searchKey: String,
    @SerializedName("html_url") val url: String,
    @SerializedName("repos_url") val reposUrl: String,
    @SerializedName("followers_url") val followersUrl: String
) {

}