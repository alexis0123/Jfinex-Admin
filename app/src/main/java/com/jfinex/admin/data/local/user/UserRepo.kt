package com.jfinex.admin.data.local.user

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserRepo @Inject constructor(private val dao: UserDao) {
    fun getUser(): Flow<User?> = dao.getUser()

    suspend fun setUser(user: User) = dao.addUser(user)
}