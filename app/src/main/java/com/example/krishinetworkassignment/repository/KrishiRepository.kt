package com.example.krishinetworkassignment.repository

import androidx.lifecycle.MutableLiveData
import com.example.krishinetworkassignment.dataClasses.OtherMandiItem
import com.example.krishinetworkassignment.database.KrishiDao
import com.example.krishinetworkassignment.networking.KrishiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class KrishiRepository(private val krishiDao: KrishiDao) {

    val otherMandi = krishiDao.getOtherMandiItems()
    val error = MutableLiveData<String>()

    suspend fun sync() {
        withContext(Dispatchers.IO) {
            runCatching {
                val response = KrishiClient.api.getOtherMandi().execute()
                if(!response.isSuccessful){
                    error.postValue("Unable to fetch data")
                    return@runCatching
                }
                val list = response.body()?.data?.otherMandi
                updateDatabase(list)
            }.onFailure {
                error.postValue("Unable to fetch data")
            }
        }
    }

    private suspend fun updateDatabase(list: List<OtherMandiItem?>?) {
        list?:return
        krishiDao.deleteOtherMandiItems()
        val nonNullList = ArrayList<OtherMandiItem>()
        for(item in list){
            item?.let {
                nonNullList.add(item)
            }
        }
        krishiDao.addOtherMandiItem(nonNullList)
    }

}