package com.waveaccess.test.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waveaccess.test.App
import com.waveaccess.test.R
import com.waveaccess.test.adapters.UsersListAdapter
import com.waveaccess.test.data.User
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
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UserViewModel::class.java]
        setHasOptionsMenu(true)
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
        recyclerView = view.findViewById(R.id.friends_list_rv)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val userDataObserver = Observer<UserDb> {
            userName.text = it.name
            val idList: List<Int> = it.friends?: listOf()
            viewModel.loadFriends(idList)
        }
        val friendsListObserver = Observer<List<UserDb>> {
            val adapter = UsersListAdapter(it)
            adapter.onItemClick = { id ->
                val fm = activity?.supportFragmentManager
                fm?.beginTransaction()
                    ?.add(R.id.fragment_container_view, newInstance(id))
                    ?.addToBackStack(null)
                    ?.commit()
            }
            recyclerView.adapter = adapter
        }
        viewModel.userData.observe(viewLifecycleOwner, userDataObserver)
        viewModel.friendsList.observe(viewLifecycleOwner, friendsListObserver)
        viewModel.loadUser(userId)
    }


    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()
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