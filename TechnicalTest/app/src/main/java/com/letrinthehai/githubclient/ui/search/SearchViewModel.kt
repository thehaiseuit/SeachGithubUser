package com.letrinthehai.githubclient.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letrinthehai.githubclient.model.User
import com.letrinthehai.githubclient.repository.SearchRepository
import com.letrinthehai.githubclient.repository.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: SearchRepository
) :  ViewModel() {

    val users = MutableLiveData<Status<List<User>>>()
    var page = 1

    fun getRepos(searchQuery: String) {
        viewModelScope.launch {
            repository.getAllUserBySearchKey(searchQuery, page).collect {
                users.postValue(it)
            }
        }
    }
}