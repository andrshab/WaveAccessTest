package com.waveaccess.test.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @ColumnInfo(name = "is_active") var is_active: Boolean?,
    @ColumnInfo(name = "eye_color") var eye_color: String,
    @ColumnInfo(name = "favorite_fruit") var favorite_fruit: String,
    @ColumnInfo(name = "registered") var registered: String,
    @ColumnInfo(name = "latitude") var latitude: Double,
    @ColumnInfo(name = "longitude") var longitude: Double,
    @ColumnInfo(name = "friends") var friends: List<Int>?,
    @PrimaryKey(autoGenerate = true) var id: Int = 0) {
}