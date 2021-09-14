package com.waveaccess.test.data

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("id")
    val userId: Int
)