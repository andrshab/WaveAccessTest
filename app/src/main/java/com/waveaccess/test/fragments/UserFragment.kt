package com.waveaccess.test.fragments

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
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
    private lateinit var nameTv: TextView
    private lateinit var ageTv: TextView
    private lateinit var companyTv: TextView
    private lateinit var emailTv: TextView
    private lateinit var phoneTv: TextView
    private lateinit var addressTv: TextView
    private lateinit var aboutTv: TextView
    private lateinit var friendsBtn: Button


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
        nameTv = view.findViewById(R.id.user_name_tv)
        ageTv = view.findViewById(R.id.user_age_tv)
        companyTv = view.findViewById(R.id.user_company_tv)
        emailTv = view.findViewById(R.id.user_email_tv)
        phoneTv = view.findViewById(R.id.user_phone_tv)
        addressTv = view.findViewById(R.id.user_address_tv)
        aboutTv = view.findViewById(R.id.user_about_tv)
        friendsBtn = view.findViewById(R.id.friends_btn)
        val userDataObserver = Observer<UserDb> {
            nameTv.text = it.name
            ageTv.text = it.age
            companyTv.text = it.company
            emailTv.text = it.email
            phoneTv.text = it.phone
            addressTv.text = it.address
            aboutTv.text = it.about
            setFriendsButton(it.friends?: listOf())
        }
        viewModel.userData.observe(viewLifecycleOwner, userDataObserver)
        viewModel.loadUser(userId)
    }

    private fun setFriendsButton(list: List<Int>) {
        val al = arrayListOf<Int>()
        al.addAll(list)
        friendsBtn.setOnClickListener {
            val fm = activity?.supportFragmentManager
            fm?.beginTransaction()
                ?.add(R.id.fragment_container_view, UsersFragment.newInstance(al))
                ?.addToBackStack(null)
                ?.commit()
        }
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