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
        private val itemHomeBinding: ItemHomeRecyclerBinding,
        private val itemEntityInterface: ItemEntityInterface
    ) :
        RecyclerView.ViewHolder(itemHomeBinding.root) {

        fun bind(item: DataItem.ItemEntity) {

            itemHomeBinding.titleTextView.text = item.title

            if (item.description == null) {
                itemHomeBinding.descriptionTextView.isGone = true
            } else {
                itemHomeBinding.descriptionTextView.isVisible = true
                itemHomeBinding.descriptionTextView.text = item.description
            }

            itemHomeBinding.priorityTextView.setOnClickListener {
                itemEntityInterface.onBumpPriority(item)
            }

            val colorRes = when (item.priority) {
                1 -> android.R.color.holo_green_dark
                2 -> android.R.color.holo_orange_dark
                3 -> android.R.color.holo_red_dark
                else -> R.color.purple_700
            }

            val color = ContextCompat.getColor(
                itemHomeBinding.root.context,
                colorRes
            )
            itemHomeBinding.priorityTextView.setBackgroundColor(color)
            itemHomeBinding.root.strokeColor = color

            itemHomeBinding.root.setOnClickListener {
                itemEntityInterface.onItemSelected(item)
            }
        }
    }

    class HeaderViewHolder(
        private val itemModelHeaderBinding: ItemModelHeaderBinding
    ) :
        RecyclerView.ViewHolder(itemModelHeaderBinding.root) {
        fun bind(item: DataItem.Header) {
            itemModelHeaderBinding.textView.text = item.headerText
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
        when (holder) {
            is HeaderViewHolder -> holder.bind(itemEntityList[position] as DataItem.Header)
            is HomeViewHolder -> holder.bind(itemEntityList[position] as DataItem.ItemEntity)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemEntityList[position]) {
            is DataItem.ItemEntity -> TYPE_ITEM
            is DataItem.Header -> TYPE_HEADER
            else -> {
                throw IllegalArgumentException("not")
            }
        }
    }

    override fun getItemCount() = itemEntityList.size
}
/*
var currentPriority = -1
if (item.priority != currentPriority) {
    currentPriority = item.priority
    itemModelHeaderBinding.textView.text = getHeaderTextForPriority(currentPriority)
}
 */
/*    private fun getHeaderTextForPriority(priority: Int): String {
        return when (priority) {
            1 -> "Low"
            2 -> "Medium"
            else -> "High"
        }
    }

 */

