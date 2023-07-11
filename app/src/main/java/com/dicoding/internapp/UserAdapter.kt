package com.dicoding.internapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(private val listener: OnUserClickListener): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    private val userList = mutableListOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = userList[position]
        holder.itemView.setOnClickListener {
            listener.onUserClick(user)
        }
        holder.emailTextView.text = user.email
        holder.nameTextView.text = "${user.first_name} ${user.last_name}"
        Glide.with(holder.itemView.context)
            .load(user.avatar)
            .into(holder.avatarImageView)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    fun setUserList(users: List<User>) {
        userList.clear()
        userList.addAll(users)
        notifyDataSetChanged()
    }

    interface OnUserClickListener {
        fun onUserClick(user: User)
    }

    class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val emailTextView = itemView.findViewById<TextView>(R.id.email_text_view)
        val nameTextView = itemView.findViewById<TextView>(R.id.name_text_view)
        val avatarImageView = itemView.findViewById<ImageView>(R.id.avatar_image_view)
    }
}
