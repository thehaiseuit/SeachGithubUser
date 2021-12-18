package com.letrinthehai.githubclient.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.letrinthehai.githubclient.model.User
import com.letrinthehai.githubclient.repository.DetailsRepository
import com.letrinthehai.githubclient.repository.Status
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val repository: DetailsRepository
) : ViewModel() {

    val user = MutableLiveData<User>()

    val isWatchersVisible = MutableLiveData<Boolean>()

    fun getUserById(userId: Long) {
        viewModelScope.launch {
            repository.getUserById(userId).collect {
                user.postValue(it)
            }
        }
    }
}