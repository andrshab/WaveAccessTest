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
            friends.map { it.userId })
    }
}