package com.letrinthehai.githubclient

import android.app.Application
import com.letrinthehai.githubclient.database.AppDatabase
import com.letrinthehai.githubclient.network.GithubApi
import com.letrinthehai.githubclient.repository.DetailsRepository
import com.letrinthehai.githubclient.repository.HomeRepository
import com.letrinthehai.githubclient.repository.SearchRepository
import com.letrinthehai.githubclient.ui.details.DetailsViewModelFactory
import com.letrinthehai.githubclient.ui.home.HomeViewModelFactory
import com.letrinthehai.githubclient.ui.search.SearchViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class MainApplication : Application(), KodeinAware {


    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@MainApplication))
        bind() from singleton { GithubApi() }
        bind() from singleton { AppDatabase(instance()) }
        bind() from singleton { SearchRepository(instance(), instance(), instance()) }
        bind() from singleton { DetailsRepository(instance()) }
        bind() from singleton { HomeRepository(instance()) }
        bind() from provider { SearchViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { DetailsViewModelFactory(instance()) }
    }


}