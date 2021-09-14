package com.waveaccess.test.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.waveaccess.test.data.local.LocalRepository
import com.waveaccess.test.data.local.UserDb
import kotlinx.coroutines.launch
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {
    val userData: MutableLiveData<UserDb> = MutableLiveData()
    val friendsList: MutableLiveData<List<UserDb>> = MutableLiveData()
    fun loadUser(id: Int) {
        viewModelScope.launch {
            userData.value = localRepository.getUser(id)
        }
    }
    fun loadFriends(list: List<Int>) {
        viewModelScope.launch {
            val fl = mutableListOf<UserDb>()
            for(id in list) {
                fl.add(localRepository.getUser(id))
            }
            friendsList.value = fl
        }
    }
}