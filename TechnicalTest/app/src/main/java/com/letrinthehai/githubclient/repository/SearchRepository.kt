package com.letrinthehai.githubclient.repository

import android.content.Context
import com.letrinthehai.githubclient.database.AppDatabase
import com.letrinthehai.githubclient.model.SearchQuery
import com.letrinthehai.githubclient.network.GithubApi
import com.letrinthehai.githubclient.model.SearchResponse
import com.letrinthehai.githubclient.model.User
import com.letrinthehai.githubclient.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import retrofit2.Response
import java.lang.Exception

class SearchRepository (
    private val api: GithubApi,
    private val db: AppDatabase,
    context: Context
) {

    private val appContext = context.applicationContext

    fun getAllUserBySearchKey(searchQuery: String, page: Int): Flow<Status<List<User>>> {
        return flow {

            if(NetworkUtils.getNetwork(appContext)){
                try {
                    val response = api.searchUsers(searchQuery, 50, page)

                    if (response.isSuccessful && response.body()?.items != null) {
                        val data = response.body()!!.items
                        val dao = db.getGithubDao()
                        if(page == 1){
                            dao.deleteUserByKey(searchQuery)
                        }

                        dao.insertQueryString(SearchQuery((searchQuery)))

                        for (user in data) {
                            user.searchKey = searchQuery
                        }
                        dao.insertUsers(data)
                    }
                } catch (e: Exception){}
            }
            val dao = db.getGithubDao()
            val users = dao.getAllUserBySearchKey(searchQuery)

            emitAll( flow { emit(users) }.map {
                Status.success(it)
            })

        }.flowOn(Dispatchers.IO)

    }

}