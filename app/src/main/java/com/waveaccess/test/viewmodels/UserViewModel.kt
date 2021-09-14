package com.waveaccess.test.viewmodels

import androidx.lifecycle.ViewModel
import com.waveaccess.test.data.local.LocalRepository
import javax.inject.Inject

class UserViewModel @Inject constructor(
    private val localRepository: LocalRepository
) : ViewModel() {

}