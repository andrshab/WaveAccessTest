package com.waveaccess.test.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.waveaccess.test.data.User

@Dao
interface UsersDao {
    @Query("SELECT * FROM users")
    suspend fun getAll(): List<User>
    @Query("SELECT * FROM users WHERE user_id = :id")
    suspend fun getUser(id: Int): User
    @Insert
    suspend fun insert(record: User)
    @Query("DELETE FROM users")
    suspend fun deleteAll()
}