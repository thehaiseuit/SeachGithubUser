package com.letrinthehai.githubclient.repository

import android.content.Context
import com.letrinthehai.githubclient.database.AppDatabase
import com.letrinthehai.githubclient.model.SearchQuery
import com.letrinthehai.githubclient.model.User
import com.letrinthehai.githubclient.network.GithubApi
import com.letrinthehai.githubclient.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class DetailsRepository(
    private val db: AppDatabase
) {

    fun getUserById(id: Long): Flow<User>{
        return flow {
            val queryStrings = db.getGithubDao().getUserById(id)
            emit(queryStrings)
        }.flowOn(Dispatchers.IO)
    }
}