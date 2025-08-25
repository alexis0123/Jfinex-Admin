package com.jfinex.admin.data.local.user

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val repo: UserRepo
): ViewModel() {
    val user: StateFlow<User?> = repo.getUser()
        .stateIn(viewModelScope, SharingStarted.Lazily, null)

    val isNewUser: StateFlow<Boolean> = user.map { it == null }
        .stateIn(viewModelScope, SharingStarted.Lazily, true)

    fun setUserTo(name: String) {
        viewModelScope.launch {
            repo.setUser(User(name = name))
        }
    }
}