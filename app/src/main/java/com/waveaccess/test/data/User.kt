package com.waveaccess.test.data

import com.google.gson.annotations.SerializedName
import com.waveaccess.test.data.local.UserDb

data class User(
    @SerializedName("id")
    val userId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("age")
    val age: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("phone")
    val phone: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("about")
    val about: String,
    @SerializedName("isActive")
    val isActive: Boolean,
    @SerializedName("eyeColor")
    val eyeColor: String,
    @SerializedName("favoriteFruit")
    val favoriteFruit: String,
    @SerializedName("registered")
    val registered: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double,
    @SerializedName("friends")
    val friends: List<User>
) {
    fun convertToDb(): UserDb {
        return UserDb(
            userId,
            name,
            age,
            company,
            email,
            phone,
            address,
            about,
            isActive,
            eyeColor,
            favoriteFruit,
            registered,
            latitude,
            longitude,
            friends.map { it.userId })
    }
}