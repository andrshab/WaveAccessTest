package com.waveaccess.test.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waveaccess.test.R
import com.waveaccess.test.data.local.UserDb

class UsersListAdapter(private val usersList: List<UserDb>):
    RecyclerView.Adapter<UsersListAdapter.ItemViewHolder>() {
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userNameTv: TextView = view.findViewById(R.id.users_list_item_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_users, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.userNameTv.text = usersList[position].user_id.toString()
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

}