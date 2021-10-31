package com.example.krishinetworkassignment.viewmodels

import androidx.lifecycle.*
import com.example.krishinetworkassignment.repository.KrishiRepository
import kotlinx.coroutines.launch

class InternetActivityViewModel(private val repository: KrishiRepository) : ViewModel() {

    val otherMandiItems = repository.otherMandi.asLiveData()
    val repositoryError = repository.error as LiveData<String?>

    fun fetch() {
        viewModelScope.launch {
            repository.sync()
        }
    }

}

class InternetActivityViewModelFactory(private val repository: KrishiRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(InternetActivityViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return InternetActivityViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}