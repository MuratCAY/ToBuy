package com.example.tobuy.model

import androidx.room.Entity
import androidx.room.PrimaryKey

sealed class DataItem {

    @Entity(tableName = "item_entity")
    data class ItemEntity(
        @PrimaryKey val id: String = "",
        val title: String = "",
        val description: String? = null,
        val priority: Int = 0,
        val createdAt: Long = 0L,
        val categoryId: String = ""
    ) : DataItem()

    @Entity(tableName = "category_entity")
    data class CategoryEntity(
        @PrimaryKey val id: String = "",
        val name: String = ""
    ): DataItem()


    data class Header(val headerText: String) : DataItem()
}