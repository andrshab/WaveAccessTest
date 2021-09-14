package com.waveaccess.test.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val USERS_LIST = "users_list"

/**
 * A simple [Fragment] subclass.
 * Use the [UsersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UsersFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: UsersViewModel
    private var list: List<Int>? = null
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[UsersViewModel::class.java]

        arguments?.let {
            list = it.getIntegerArrayList(USERS_LIST)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.users_list_rv)
        recyclerView.layoutManager = LinearLayoutManager(context)
        val usersListObserver = Observer<List<UserDb>> {
            recyclerView.adapter = UsersListAdapter(it)
        }
        viewModel.usersList.observe(viewLifecycleOwner, usersListObserver)

        viewModel.refreshUsers()
    }

    companion object {
        @JvmStatic
        fun newInstance(list: String) =
            UsersFragment().apply {
                arguments = Bundle().apply {
                    putString(USERS_LIST, list)
                }
            }
    }
}