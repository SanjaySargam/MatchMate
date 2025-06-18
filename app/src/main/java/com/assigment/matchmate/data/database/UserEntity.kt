package com.assigment.matchmate.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,

    val firstName: String,
    val lastName: String,
    val age: Int,

    val city: String,
    val state: String,
    val country: String,

    val imageUrl: String,
    val email: String,
    val phone: String,
    val gender: String,

    val education: String,
    val occupation: String,

    val matchScore: Int,

    val status: UserStatus = UserStatus.PENDING,

    val createdAt: Long = System.currentTimeMillis()
)

enum class UserStatus {
    PENDING,
    ACCEPTED,
    DECLINED
}