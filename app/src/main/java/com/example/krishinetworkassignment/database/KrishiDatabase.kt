package com.example.krishinetworkassignment.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.krishinetworkassignment.dataClasses.OtherMandiItem

@Database(entities = [OtherMandiItem::class], version = 1)
abstract class KrishiDatabase : RoomDatabase() {

    abstract fun krishiDao(): KrishiDao

    companion object {
        @Volatile
        private var INSTANCE: KrishiDatabase? = null

        fun getDatabase(context: Context): KrishiDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    KrishiDatabase::class.java,
                    "KrishiDatabase"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}