package com.waveaccess.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserDb>
    @Query("SELECT * FROM users WHERE user_id = :id")
    suspend fun getUser(id: Int): UserDb
    @Insert
    suspend fun insert(record: UserDb)
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}