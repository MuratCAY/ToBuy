package com.example.tobuy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tobuy.R
import com.example.tobuy.databinding.ItemHomeRecyclerBinding
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.ui.home.ItemEntityInterface

class HomeAdapter(
    private val itemEntityList: ArrayList<ItemEntity>,
    private val itemEntityInterface: ItemEntityInterface
) :
    RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    inner class HomeViewHolder(
        val itemBinding: ItemHomeRecyclerBinding,
        val itemEntityInterface: ItemEntityInterface
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: ItemEntity) {
            itemBinding.titleTextView.text = item.title
            if (item.description == null) {
                itemBinding.descriptionTextView.isGone = true
            } else {
                itemBinding.descriptionTextView.isVisible = true
                itemBinding.descriptionTextView.text = item.description
            }
            itemBinding.deleteImageView.setOnClickListener {
                itemEntityInterface.onDeleteItemEntity(item)
            }
            itemBinding.priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(item)
            }
            val colorRes = when (item.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.purple_700
            }
            itemBinding.priorityTextView.setBackgroundColor(
                ContextCompat.getColor(
                    itemBinding.root.context,
                    colorRes
                )
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            ItemHomeRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(view, itemEntityInterface)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(itemEntityList[position])
    }

    override fun getItemCount() = itemEntityList.size
}