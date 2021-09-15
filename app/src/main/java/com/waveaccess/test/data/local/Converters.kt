package com.waveaccess.test.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson

import com.google.gson.reflect.TypeToken
import com.waveaccess.test.data.User


class Converters {

    @TypeConverter
    fun restoreList(listOfInt: String?): List<Int?>? {
        return Gson().fromJson(listOfInt, object : TypeToken<List<Int?>?>() {}.type)
    }

    @TypeConverter
    fun saveList(listOfInt: List<Int?>?): String? {
        return Gson().toJson(listOfInt)
    }

    @TypeConverter
    fun restoreUserList(listOfUser: String?): List<User?>? {
        return Gson().fromJson(listOfUser, object : TypeToken<List<User?>?>() {}.type)
    }

    @TypeConverter
    fun saveUserList(listOfUser: List<User?>?): String? {
        return Gson().toJson(listOfUser)
    }

}