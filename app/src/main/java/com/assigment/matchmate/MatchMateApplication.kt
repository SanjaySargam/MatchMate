package com.assigment.matchmate

import android.app.Application
import com.assigment.matchmate.data.database.AppDatabase
import com.assigment.matchmate.data.repository.UserRepository

class MatchMateApplication : Application() {

    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao()) }
}