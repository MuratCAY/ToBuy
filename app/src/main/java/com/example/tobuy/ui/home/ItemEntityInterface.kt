package com.example.tobuy.ui.home

import com.example.tobuy.model.ItemEntity

interface ItemEntityInterface {
    fun onDeleteItemEntity(itemEntity: ItemEntity)
    fun onBumpPriority(itemEntity: ItemEntity)
}