package com.waveaccess.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.waveaccess.test.App
import com.waveaccess.test.R
import com.waveaccess.test.data.local.UserDb
import com.waveaccess.test.viewmodels.UserViewModel
import com.waveaccess.test.viewmodels.ViewModelFactory
import javax.inject.Inject

private const val USER_ID = "user_id"

class UserFragment : Fragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UserViewModel
    private var userId: Int = 0
    private lateinit var userName: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]

        arguments?.let {
            userId = it.getInt(USER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userName = view.findViewById(R.id.user_name_tv)
        val userDataObserver = Observer<UserDb> {
            userName.text = it.name
            val idList: List<Int> = it.friends?: listOf()
            val arrayList = arrayListOf<Int>()
            for(id in idList) {
                arrayList.add(id)
            }

            childFragmentManager.beginTransaction()
                .replace(R.id.child_fragment_container, UsersFragment.newInstance(arrayList))
                .commit()
        }
        viewModel.userData.observe(viewLifecycleOwner, userDataObserver)
        viewModel.loadUser(userId)
    }

    companion object {
        @JvmStatic
        fun newInstance(userId: Int) =
            UserFragment().apply {
                arguments = Bundle().apply {
                    putInt(USER_ID, userId)
                }
            }
    }
}