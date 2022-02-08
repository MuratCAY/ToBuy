package com.example.tobuy.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.tobuy.R
import com.example.tobuy.databinding.ItemHomeRecyclerBinding
import com.example.tobuy.databinding.ItemModelHeaderBinding
import com.example.tobuy.model.DataItem
import com.example.tobuy.ui.home.ItemEntityInterface

class HomeAdapter(
    private val itemEntityList: List<DataItem>,
    private val itemEntityInterface: ItemEntityInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    class HomeViewHolder(
        private val itemBinding: ItemHomeRecyclerBinding,
        private val itemEntityInterface: ItemEntityInterface
    ) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(item: DataItem.ItemEntity) {

            itemBinding.titleTextView.text = item.title

            if (item.description == null) {
                itemBinding.descriptionTextView.isGone = true
            } else {
                itemBinding.descriptionTextView.isVisible = true
                itemBinding.descriptionTextView.text = item.description
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

            val color = ContextCompat.getColor(
                itemBinding.root.context,
                colorRes
            )
            itemBinding.priorityTextView.setBackgroundColor(color)
            itemBinding.root.strokeColor = color

        }
    }

    class HeaderViewHolder(private val itemModelHeaderBinding: ItemModelHeaderBinding) :
        RecyclerView.ViewHolder(itemModelHeaderBinding.root) {
        fun bind(header: DataItem.Header) {
            itemModelHeaderBinding.textView.text = header.headerText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> HeaderViewHolder(
                ItemModelHeaderBinding.inflate(
                    LayoutInflater.from(
                        parent.context
                    ), parent, false
                )
            )
            TYPE_ITEM -> HomeViewHolder(
                ItemHomeRecyclerBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), itemEntityInterface
            )
            else -> throw IllegalArgumentException("Invalid ViewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is HomeViewHolder -> holder.bind(itemEntityList[position] as DataItem.ItemEntity)
            is HeaderViewHolder -> holder.bind(itemEntityList[position] as DataItem.Header)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemEntityList[position]) {
            is DataItem.ItemEntity -> TYPE_ITEM
            is DataItem.Header -> TYPE_HEADER
        }
    }

    override fun getItemCount() = itemEntityList.size
}

