package com.example.tobuy.ui.home

import android.os.Bundle
import android.view.View
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentHomeBinding
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.ui.adapter.HomeAdapter
import com.example.tobuy.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate),
    ItemEntityInterface {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            navigateViaNavGraph(R.id.action_homeFragment_to_addItemEntityFragment)
        }
        sharedViewModel.itemEntitiesLiveData.observe(viewLifecycleOwner) { itemEntityList ->
            val adapter = HomeAdapter(itemEntityList as ArrayList<ItemEntity>, this)
            binding.recyclerView.adapter = adapter
        }
    }

    override fun onDeleteItemEntity(itemEntity: ItemEntity) {
        sharedViewModel.deleteItem(itemEntity)
    }

    override fun onBumpPriority(itemEntity: ItemEntity) {
        // TODO: implement me!
    }
}