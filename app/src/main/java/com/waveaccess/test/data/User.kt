package com.waveaccess.test.data

import com.google.gson.annotations.SerializedName
import com.waveaccess.test.data.local.UserDb

data class User(
    @SerializedName("id")
    val userId: Int
) {
    fun convertToDb(): UserDb {
        return UserDb(userId)
    }
}