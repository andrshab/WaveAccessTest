package com.waveaccess.test.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserDb (
    @ColumnInfo(name = "user_id") var user_id: Int?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) {
}