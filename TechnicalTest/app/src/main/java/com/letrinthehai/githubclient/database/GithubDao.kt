package com.letrinthehai.githubclient.database

import androidx.room.*
import com.letrinthehai.githubclient.model.SearchQuery
import com.letrinthehai.githubclient.model.User

@Dao
abstract class GithubDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insertUsers(user: List<User>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertQueryString(queryString: SearchQuery)

    @Transaction
    @Query("SELECT * FROM User WHERE User.searchKey = :key")
    abstract fun getAllUserBySearchKey(key: String) : List<User>

    @Transaction
    @Query("SELECT * FROM User WHERE User.id = :userId")
    abstract fun getUserById(userId: Long) : User

    @Transaction
    @Query("SELECT * FROM SearchQuery")
    abstract fun getQueryString():  List<SearchQuery>

    @Query("DELETE FROM User WHERE User.searchKey = :key")
    abstract fun deleteUserByKey(key: String)
}