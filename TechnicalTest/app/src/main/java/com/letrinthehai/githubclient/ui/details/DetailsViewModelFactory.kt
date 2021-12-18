package com.letrinthehai.githubclient.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.letrinthehai.githubclient.repository.DetailsRepository

class DetailsViewModelFactory(
    private val repository: DetailsRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DetailsViewModel(repository) as T
    }
}