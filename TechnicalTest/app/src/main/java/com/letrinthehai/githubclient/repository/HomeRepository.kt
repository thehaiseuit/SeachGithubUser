package com.letrinthehai.githubclient.repository

import com.letrinthehai.githubclient.database.AppDatabase
import com.letrinthehai.githubclient.model.SearchQuery
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class HomeRepository(
    private val db: AppDatabase
) {

    fun getSearchQuery(): Flow<List<SearchQuery>>{
        return flow {
            val queryStrings = db.getGithubDao().getQueryString()
            emit(queryStrings)
        }.flowOn(Dispatchers.IO)
    }
}