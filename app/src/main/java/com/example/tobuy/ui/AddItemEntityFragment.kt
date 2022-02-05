package com.example.tobuy.ui

import android.os.Bundle
import android.view.View
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentAddItemEntityBinding
import com.example.tobuy.model.ItemEntity
import com.example.tobuy.ui.base.BaseFragment
import java.util.*

class AddItemEntityFragment :
    BaseFragment<FragmentAddItemEntityBinding>(FragmentAddItemEntityBinding::inflate) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }
    }

    private fun saveItemEntityToDatabase() {
        val itemTitle = binding.titleEditTet.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = "* Required Field"
            return
        }
        binding.titleTextField.error = null

        val itemDescription = binding.descriptionEditTet.text.toString().trim()

        val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }
        val itemEntity = ItemEntity(
            UUID.randomUUID().toString(),
            itemTitle,
            itemDescription,
            itemPriority,
            System.currentTimeMillis()
        )
        sharedViewModel.insertItem(itemEntity)
    }
}