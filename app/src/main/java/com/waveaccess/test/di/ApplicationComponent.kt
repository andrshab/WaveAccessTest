package com.waveaccess.test.di

import com.waveaccess.test.MainActivity
import com.waveaccess.test.data.local.AppDatabase
import com.waveaccess.test.fragments.UserFragment
import com.waveaccess.test.fragments.UsersFragment
import dagger.Component
import javax.inject.Singleton


@Component(modules = [NetworkModule::class, DataBaseModule::class])
@Singleton
interface ApplicationComponent {
    fun inject(activity: MainActivity)
    fun inject(fragment: UserFragment)
    fun inject(fragment: UsersFragment)
    fun dataBase(): AppDatabase
}