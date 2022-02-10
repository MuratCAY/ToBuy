package com.example.tobuy.ui.home

import com.example.tobuy.model.DataItem

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: DataItem.ItemEntity)
    fun onItemSelected(itemEntity: DataItem.ItemEntity)
}