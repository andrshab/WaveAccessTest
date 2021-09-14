package com.waveaccess.test.data.local

import javax.inject.Inject

class LocalRepository @Inject constructor(private val db: AppDatabase) {
    suspend fun saveRecord(userId: Int?) {
        db.usersDao().insert(User(userId))
    }
    suspend fun getAll(): List<User> {
        return db.usersDao().getAll()
    }
    suspend fun clear() {
        db.usersDao().deleteAll()
    }
}