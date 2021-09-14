package com.waveaccess.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.lifecycleScope
import com.waveaccess.test.data.RemoteRepository
import com.waveaccess.test.data.local.LocalRepository
import com.waveaccess.test.fragments.UsersFragment
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<UsersFragment>(R.id.fragment_container_view)
            addToBackStack(null)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val index = supportFragmentManager.backStackEntryCount - 1
        val tag = supportFragmentManager.getBackStackEntryAt(index)
        if(tag.equals("main")) {

        }
    }
}