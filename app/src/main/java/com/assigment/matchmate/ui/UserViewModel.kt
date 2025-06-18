package com.assigment.matchmate.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assigment.matchmate.data.database.UserEntity
import com.assigment.matchmate.data.database.UserStatus
import com.assigment.matchmate.data.repository.UserRepository
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    val users: LiveData<List<UserEntity>> = repository.getAllUsers()

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    private val _retryCount = MutableLiveData<Int>()
    val retryCount: LiveData<Int> = _retryCount

    init {
        loadUsers()
    }

    fun loadUsers() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val userCount = repository.getUserCount()
            if (userCount == 0) {
                fetchUsersWithRetry()
            } else {
                _loading.value = false
            }
        }
    }

    private suspend fun fetchUsersWithRetry(maxRetries: Int = 3) {
        var attempts = 0

        while (attempts < maxRetries) {
            attempts++
            _retryCount.value = attempts

            val result = repository.fetchAndStoreUsers()

            if (result.isSuccess) {
                _loading.value = false
                _error.value = null
                return
            } else {
                if (attempts >= maxRetries) {
                    _loading.value = false
                    _error.value = "Failed to load data after $maxRetries attempts. Using offline data."
                }
                // getting error here so commenting code as of now
//                delay(1000 * attempts)
            }
        }
    }

    fun acceptUser(userId: String) {
        viewModelScope.launch {
            repository.updateUserStatus(userId, UserStatus.ACCEPTED)
        }
    }

    fun declineUser(userId: String) {
        viewModelScope.launch {
            repository.updateUserStatus(userId, UserStatus.DECLINED)
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _loading.value = true
            fetchUsersWithRetry()
        }
    }

    fun clearError() {
        _error.value = null
    }
}