package com.letrinthehai.githubclient.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letrinthehai.githubclient.model.SearchQuery
import com.letrinthehai.githubclient.repository.HomeRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository
)  :  ViewModel() {

    val queryStrings = MutableLiveData<List<SearchQuery>>()

    fun getSearchQuery(){
        viewModelScope.launch {
            repository.getSearchQuery().collect {
                queryStrings.postValue(it)
            }
        }
    }
}