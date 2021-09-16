package com.waveaccess.test.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "users")
data class User (
    @SerializedName("id")
    @ColumnInfo(name = "user_id")
    var user_id: Int?,
    @SerializedName("name")
    @ColumnInfo(name = "name")
    var name: String?,
    @SerializedName("age")
    @ColumnInfo(name = "age")
    var age: String?,
    @SerializedName("company")
    @ColumnInfo(name = "company")
    var company: String?,
    @SerializedName("email")
    @ColumnInfo(name = "email")
    var email: String?,
    @SerializedName("phone")
    @ColumnInfo(name = "phone")
    var phone: String?,
    @SerializedName("address")
    @ColumnInfo(name = "address")
    var address: String?,
    @SerializedName("about")
    @ColumnInfo(name = "about")
    var about: String?,
    @SerializedName("isActive")
    @ColumnInfo(name = "is_active")
    var is_active: Boolean?,
    @SerializedName("eyeColor")
    @ColumnInfo(name = "eye_color")
    var eye_color: String,
    @SerializedName("favoriteFruit")
    @ColumnInfo(name = "favorite_fruit")
    var favorite_fruit: String,
    @SerializedName("registered")
    @ColumnInfo(name = "registered")
    var registered: String,
    @SerializedName("latitude")
    @ColumnInfo(name = "latitude")
    var latitude: Double,
    @SerializedName("longitude")
    @ColumnInfo(name = "longitude")
    var longitude: Double,
    @ColumnInfo(name = "friends")
    @SerializedName("friends")
    var friends: List<User>?,
    @PrimaryKey(autoGenerate = true) var _id: Int = 0) {
    val friendsIds: List<Int>?
        get() = friends?.map { it.user_id?:0 }
}