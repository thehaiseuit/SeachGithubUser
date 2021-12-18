package com.letrinthehai.githubclient.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class SearchQuery (
    @PrimaryKey
    val queryString: String
    )