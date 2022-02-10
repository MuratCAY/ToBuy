package com.example.tobuy.ui.home

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentHomeBinding
import com.example.tobuy.model.DataItem
import com.example.tobuy.ui.adapter.HomeAdapter
import com.example.tobuy.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ItemEntityInterface {

    private lateinit var adapter: HomeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigateFabClick()
        observeItemEntityList()
    }

    private fun navigateFabClick() {
        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addItemEntityFragment)
        }
    }

    private fun observeItemEntityList() {
        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            adapter = HomeAdapter(itemEntityList, this)
            binding.recyclerView.adapter = adapter

            val itemTouchHelper =
                ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        sharedViewModel.deleteItem(itemEntityList[viewHolder.adapterPosition])
                    }
                })
            itemTouchHelper.attachToRecyclerView(binding.recyclerView)
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivity.hideKeyboard(requireView())
    }

    override fun onBumpPriority(itemEntity: DataItem.ItemEntity) {
        val currentPriority = itemEntity.priority
        var newPriority = currentPriority + 1
        if (newPriority > 3) {
            newPriority = 1
        }
        val updateItemEntity = itemEntity.copy(priority = newPriority)
        sharedViewModel.updateItem(updateItemEntity)
    }

    override fun onItemSelected(itemEntity: DataItem.ItemEntity) {
        val navDirections = HomeFragmentDirections.actionHomeFragmentToAddItemEntityFragment(itemEntity.id)
        findNavController().navigate(navDirections)
    }
}