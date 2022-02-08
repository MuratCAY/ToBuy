package com.example.tobuy.arch

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tobuy.database.db.AppDatabase
import com.example.tobuy.model.DataItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ToBuyViewModel : ViewModel() {

    private lateinit var repository: ToBuyRepository

    val itemEntitiesLiveData = MutableLiveData<List<DataItem.ItemEntity>>()
    val transactionCompleteLiveData = MutableLiveData<Boolean>()

    fun init(appDatabase: AppDatabase) {
        repository = ToBuyRepository(appDatabase)

        viewModelScope.launch {
            repository.getAllItems().collect { items ->
                itemEntitiesLiveData.postValue(items.sortedByDescending { it.priority })
            }
        }
    }

    fun insertItem(itemEntity: DataItem.ItemEntity) {
        viewModelScope.launch {
            repository.insertItem(itemEntity)
            transactionCompleteLiveData.postValue(true)
        }
    }

    fun deleteItem(itemEntity: DataItem.ItemEntity) {
        viewModelScope.launch {
            repository.deleteItem(itemEntity)
        }
    }

    fun updateItem(itemEntity: DataItem.ItemEntity) {
        viewModelScope.launch {
            repository.updateItem(itemEntity)
        }
    }
}