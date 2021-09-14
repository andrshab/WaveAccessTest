package com.waveaccess.test.data

import com.waveaccess.test.api.ApiService
import javax.inject.Inject

class RemoteRepository @Inject constructor(private val service: ApiService) {
    suspend fun getUsers(): List<User> {
        return service.users()
    }
}