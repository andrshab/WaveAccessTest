package com.waveaccess.test.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waveaccess.test.api.ErrorCallback
import com.waveaccess.test.api.Exceptions
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
    lateinit var networkError: () -> Unit
    fun checkUsers() {
        viewModelScope.launch {
            val users = localRepository.getAll()
            if(users.isNotEmpty()) {
                usersList.value = users
            } else {
                refreshUsers()
            }
        }
    }
    fun refreshUsers() {
        viewModelScope.launch(Exceptions.handler + ErrorCallback{networkError.invoke()}) {
            val users = remoteRepository.getUsers()
            localRepository.clear()
            for(user in users) {
                localRepository.saveUser(user)
            }
            usersList.value = localRepository.getAll()
        }
    }
    fun loadUsers(list: List<Int>?) {
        viewModelScope.launch {
            val ul = mutableListOf<UserDb>()
            if (list != null) {
                for(id in list) {
                    ul.add(localRepository.getUser(id))
                }
            }
            usersList.value = ul
        }
    }

}