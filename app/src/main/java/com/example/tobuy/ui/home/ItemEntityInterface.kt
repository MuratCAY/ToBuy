package com.example.tobuy.ui.home

import com.example.tobuy.model.ItemEntity

interface ItemEntityInterface {
    fun onBumpPriority(itemEntity: ItemEntity)
}