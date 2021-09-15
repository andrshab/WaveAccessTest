package com.waveaccess.test.data.local

import com.waveaccess.test.data.User
import javax.inject.Inject

class LocalRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun saveUser(user: User) {
        db.usersDao().insert(user)
    }
    suspend fun getAll(): List<User> {
        return db.usersDao().getAll()
    }
    suspend fun getUser(id: Int): User {
        return db.usersDao().getUser(id)
    }
    suspend fun clear() {
        db.usersDao().deleteAll()
    }
}