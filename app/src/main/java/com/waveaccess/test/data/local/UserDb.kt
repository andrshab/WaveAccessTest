package com.waveaccess.test.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.waveaccess.test.data.User

@Entity(tableName = "users")
data class UserDb (
    @ColumnInfo(name = "user_id") var user_id: Int?,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "age") var age: String?,
    @ColumnInfo(name = "company") var company: String?,
    @ColumnInfo(name = "email") var email: String?,
    @ColumnInfo(name = "phone") var phone: String?,
    @ColumnInfo(name = "address") var address: String?,
    @ColumnInfo(name = "about") var about: String?,
    @ColumnInfo(name = "friends") var friends: List<Int>?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) {
}