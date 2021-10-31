package com.example.krishinetworkassignment.utils

import android.app.Application
import com.example.krishinetworkassignment.database.KrishiDatabase
import com.example.krishinetworkassignment.repository.KrishiRepository

class KrishiApplication : Application() {
    private val database by lazy {
        KrishiDatabase.getDatabase(this)
    }
    val repository by lazy {
        KrishiRepository(database.krishiDao())
    }
}