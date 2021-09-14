package com.waveaccess.test.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.waveaccess.test.data.RemoteRepository
import com.waveaccess.test.data.local.LocalRepository
import javax.inject.Inject

class ViewModelFactory @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UsersViewModel::class.java)) {
            return UsersViewModel(remoteRepository, localRepository) as T
        } else if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(localRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}