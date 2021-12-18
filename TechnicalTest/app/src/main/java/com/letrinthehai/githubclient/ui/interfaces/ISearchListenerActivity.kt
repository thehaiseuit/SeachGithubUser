package com.letrinthehai.githubclient.ui.interfaces

interface ISearchListenerActivity {
    fun showSearchView(isShown: Boolean)
    fun setSearchText(searchQuery: String)
    fun registerSearchFragment(instance: ISearchListenerFragment)
}