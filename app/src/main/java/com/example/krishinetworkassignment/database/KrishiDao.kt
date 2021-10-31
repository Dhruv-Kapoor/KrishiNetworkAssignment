package com.example.krishinetworkassignment.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.krishinetworkassignment.dataClasses.OtherMandiItem
import kotlinx.coroutines.flow.Flow

@Dao
interface KrishiDao {

    @Query("SELECT * FROM OtherMandiItem")
    fun getOtherMandiItems(): Flow<List<OtherMandiItem>>

    @Insert
    suspend fun addOtherMandiItem(vararg item: OtherMandiItem)

    @Insert
    suspend fun addOtherMandiItem(items: List<OtherMandiItem>)

    @Query("DELETE FROM OtherMandiItem")
    suspend fun deleteOtherMandiItems()

}