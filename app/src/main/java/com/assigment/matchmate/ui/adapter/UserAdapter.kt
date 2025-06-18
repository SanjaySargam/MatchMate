package com.assigment.matchmate.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.assigment.matchmate.data.database.UserEntity
import com.assigment.matchmate.data.database.UserStatus
import com.bumptech.glide.Glide
import com.assigment.matchmate.R

class UserAdapter(
    private val onAccept: (String) -> Unit,
    private val onDecline: (String) -> Unit,
    private val showImages: Boolean = true
) : ListAdapter<UserEntity, UserAdapter.UserViewHolder>(UserDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_card, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val profileImage: ImageView = itemView.findViewById(R.id.profileImage)
        private val nameText: TextView = itemView.findViewById(R.id.nameText)
        private val ageLocationText: TextView = itemView.findViewById(R.id.ageLocationText)
        private val educationText: TextView = itemView.findViewById(R.id.educationText)
        private val occupationText: TextView = itemView.findViewById(R.id.occupationText)
        private val matchScoreText: TextView = itemView.findViewById(R.id.matchScoreText)
        private val acceptButton: Button = itemView.findViewById(R.id.acceptButton)
        private val declineButton: Button = itemView.findViewById(R.id.declineButton)
        private val statusText: TextView = itemView.findViewById(R.id.statusText)

        fun bind(user: UserEntity) {
            nameText.text = "${user.firstName} ${user.lastName}"
            ageLocationText.text = "${user.age}, ${user.city}, ${user.state}"
            educationText.text = user.education
            occupationText.text = user.occupation
            matchScoreText.text = "${user.matchScore}% Match"

            // Handle image display based on showImages flag
            if (showImages) {
                profileImage.visibility = View.VISIBLE
                Glide.with(itemView.context)
                    .load(user.imageUrl)
                    .placeholder(R.drawable.ic_person_placeholder)
                    .error(R.drawable.ic_person_placeholder)
                    .into(profileImage)
            } else {
                profileImage.visibility = View.GONE
            }

            // Update UI based on user status
            when (user.status) {
                UserStatus.PENDING -> {
                    acceptButton.visibility = View.VISIBLE
                    declineButton.visibility = View.VISIBLE
                    statusText.visibility = View.GONE
                    itemView.alpha = 1.0f
                }
                UserStatus.ACCEPTED -> {
                    acceptButton.visibility = View.GONE
                    declineButton.visibility = View.GONE
                    statusText.visibility = View.VISIBLE
                    statusText.text = "ACCEPTED"
                    statusText.setTextColor(ContextCompat.getColor(itemView.context, R.color.green))
                    itemView.alpha = 0.7f
                }
                UserStatus.DECLINED -> {
                    acceptButton.visibility = View.GONE
                    declineButton.visibility = View.GONE
                    statusText.visibility = View.VISIBLE
                    statusText.text = "DECLINED"
                    statusText.setTextColor(ContextCompat.getColor(itemView.context, R.color.red))
                    itemView.alpha = 0.7f
                }
            }

            acceptButton.setOnClickListener {
                onAccept(user.id)
            }

            declineButton.setOnClickListener {
                onDecline(user.id)
            }
        }
    }

    class UserDiffCallback : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem == newItem
        }
    }
}