package com.waveaccess.test.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waveaccess.test.data.RemoteRepository
import com.waveaccess.test.data.local.LocalRepository
import com.waveaccess.test.data.local.UserDb
import kotlinx.coroutines.launch
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val localRepository: LocalRepository
): ViewModel() {
    val usersList: MutableLiveData<List<UserDb>> = MutableLiveData()
    fun refreshUsers() {
        viewModelScope.launch {
            val users = remoteRepository.getUsers()
            for(user in users) {
                localRepository.saveUser(user)
            }
            usersList.value = localRepository.getAll()
        }
    }
    fun loadUsers(list: List<Int>? = null) {
        viewModelScope.launch {
            if(list == null) {
                usersList.value = localRepository.getAll()// TODO change to getWithIds
            } else {
                usersList.value = localRepository.getAll()
            }
        }
    }

}