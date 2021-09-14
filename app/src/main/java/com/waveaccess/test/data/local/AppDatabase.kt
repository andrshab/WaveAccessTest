package com.waveaccess.test.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserDb::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usersDao(): UsersDao
}