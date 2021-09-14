package com.waveaccess.test.api

import com.waveaccess.test.data.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v0/b/candidates--questionnaire.appspot.com/o/users.json?alt=media&token=e3672c23-b1a5-4ca7-bb77-b6580d75810c")
    suspend fun users(): List<User>

    companion object Factory {
        fun create(): ApiService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(ApiConstants.BASE_URL)
                .build()
                .create(ApiService::class.java)
        }
    }
}