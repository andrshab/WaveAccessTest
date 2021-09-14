package com.waveaccess.test.di

import com.waveaccess.test.api.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class NetworkModule {
    @Provides
    @Singleton
    fun provideRetrofitService(): ApiService {
        return ApiService.create()
    }
}