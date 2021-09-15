package com.waveaccess.test.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.waveaccess.test.App
import com.waveaccess.test.R
import com.waveaccess.test.adapters.UsersListAdapter
import com.waveaccess.test.data.local.UserDb
import com.waveaccess.test.viewmodels.UsersViewModel
import com.waveaccess.test.viewmodels.ViewModelFactory
import javax.inject.Inject

private const val USERS_LIST = "users_list"
private const val USER_NAME = "user_name"

class UsersFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UsersViewModel
    private var list: List<Int>? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var friendsTitleTv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.networkError = {
            Toast.makeText(context, getString(R.string.load_error), Toast.LENGTH_SHORT).show()
        }
        recyclerView = view.findViewById(R.id.users_list_rv)
        friendsTitleTv = view.findViewById(R.id.friends_title_tv)
        friendsTitleTv.visibility = View.GONE
        recyclerView.layoutManager = LinearLayoutManager(context)
        val usersListObserver = Observer<List<UserDb>> {
            val adapter = UsersListAdapter(it)
            adapter.onItemClick = { id, isActive ->
                if(isActive) {
                    val fm = activity?.supportFragmentManager
                    fm?.beginTransaction()
                        ?.add(R.id.fragment_container_view, UserFragment.newInstance(id))
                        ?.addToBackStack(null)
                        ?.commit()
                }
            }
            recyclerView.adapter = adapter
        }
        viewModel.usersList.observe(viewLifecycleOwner, usersListObserver)
        if(arguments == null) {
            viewModel.checkUsers()
        } else {
            arguments?.let {
                list = it.getIntegerArrayList(USERS_LIST)
                friendsTitleTv.visibility = View.VISIBLE
                friendsTitleTv.text = String.format(getString(R.string.friends_list_title), it.getString(USER_NAME))
                viewModel.loadUsers(list)
            }
        }

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_users, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_users_rerfresh -> viewModel.refreshUsers()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun newInstance(list: ArrayList<Int>?, name: String?) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putIntegerArrayList(USERS_LIST, list)
                    putString(USER_NAME, name)
                }
            }
    }
}