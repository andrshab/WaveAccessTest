package com.waveaccess.test.adapters

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.waveaccess.test.R
import com.waveaccess.test.data.local.UserDb

class UsersListAdapter(private val usersList: List<UserDb>):
    RecyclerView.Adapter<UsersListAdapter.ItemViewHolder>() {
    var onItemClick: ((Int, Boolean) -> Unit)? = null
    inner class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val userNameTv: TextView = view.findViewById(R.id.users_list_item_name)
        val emailTv: TextView = view.findViewById(R.id.users_list_item_email)
        val isActive: View = view.findViewById(R.id.is_active_view)
        init {
            view.setOnClickListener {
                val userId = usersList[adapterPosition].user_id
                val isActive = usersList[adapterPosition].is_active?: false
                if(userId!=null) {
                    onItemClick?.invoke(userId, isActive)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_users, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.userNameTv.text = usersList[position].name
        holder.emailTv.text = usersList[position].email
        holder.isActive.setBackgroundColor(isActiveColor(usersList[position].is_active?: false))
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    private fun isActiveColor(isActive: Boolean): Int {
        return if(isActive) Color.GREEN else Color.GRAY
    }

}