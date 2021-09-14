package com.waveaccess.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.waveaccess.test.data.RemoteRepository
import com.waveaccess.test.data.local.LocalRepository
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject lateinit var remoteRepository: RemoteRepository
    @Inject lateinit var localRepository: LocalRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        (applicationContext as App).appComponent.inject(this)
        lifecycleScope.launch {
            val users = remoteRepository.getUsers()
            Log.d("MainActivity", users.toString())
            for(user in users) {
                localRepository.saveRecord(user.userId)
            }
            val savedUsers = localRepository.getAll()
            Log.d("MainActivity", savedUsers.toString())
        }
    }
}