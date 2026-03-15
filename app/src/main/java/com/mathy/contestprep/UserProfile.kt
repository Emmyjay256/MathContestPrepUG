package com.mathy.contestprep

data class UserProfile(
    val uid: String,
    val name: String,
    val email: String,
    val avatarId: Int,
    val credits: Int = 0,
    val streak: Int = 0,
    val solvedCount: Int = 0,
    val lastLogin: Long = System.currentTimeMillis()
)