package com.letrinthehai.githubclient.network

import com.letrinthehai.githubclient.model.SearchResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL: String = "https://api.github.com/"
interface GithubApi {

    companion object {
        operator fun invoke(): GithubApi {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor(logging)
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build()
                .create(GithubApi::class.java)
        }
    }

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") searchQuery: String,
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): Response<SearchResponse>
}