package com.letrinthehai.githubclient.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.letrinthehai.githubclient.model.SearchQuery
import com.letrinthehai.githubclient.model.User

@Database(
    entities = [User::class, SearchQuery::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getGithubDao() : GithubDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "githubclient"
        ).build()

    }
}