package com.example.tobuy.ui.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.tobuy.R
import com.example.tobuy.databinding.FragmentAddItemEntityBinding
import com.example.tobuy.model.DataItem
import com.example.tobuy.ui.base.BaseFragment
import java.util.*

class AddItemEntityFragment : BaseFragment<FragmentAddItemEntityBinding>(FragmentAddItemEntityBinding::inflate) {

    private val safeArgs: AddItemEntityFragmentArgs by navArgs()

    private val selectedItemEntity: DataItem.ItemEntity? by lazy {
        sharedViewModel.itemEntitiesLiveData.value?.find {
            it.id == safeArgs.selectedItemEntityId
        }
    }

    private var isInEditMode: Boolean = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        clickSaveButton()
        observeTransaction()
        setupEditScreen()
    }

    private fun clickSaveButton() {
        binding.saveButton.setOnClickListener {
            saveItemEntityToDatabase()
        }
    }

    private fun observeTransaction() {
        sharedViewModel.transactionCompleteLiveData.observe(viewLifecycleOwner) { complete ->
            if (complete) {

                if (isInEditMode) {
                    navigateUp()
                    return@observe
                }

                Toast.makeText(requireContext(), "Item saved!", Toast.LENGTH_SHORT).show()
                binding.apply {
                    titleEditText.text = null
                    titleEditText.requestFocus()
                    mainActivity.showKeyboard(requireView())
                    descriptionEditText.text = null
                    radioGroup.check(R.id.radioButtonLow)
                }
            }
        }
    }

    private fun setupEditScreen() {
        selectedItemEntity?.let { itemEntity ->
            isInEditMode = true

            binding.apply {
                titleEditText.setText(itemEntity.title)
                descriptionEditText.setText(itemEntity.description)
                binding.titleEditText.setSelection(itemEntity.title.length)
                saveButton.text = "Update !"
            }
            when (itemEntity.priority) {
                1 -> binding.radioGroup.check(R.id.radioButtonLow)
                2 -> binding.radioGroup.check(R.id.radioButtonMedium)
                else -> binding.radioGroup.check(R.id.radioButtonHigh)
            }
            mainActivity.supportActionBar?.title = "Update item !"
        }
    }

    override fun onPause() {
        super.onPause()
        sharedViewModel.transactionCompleteLiveData.postValue(false)
    }

    private fun saveItemEntityToDatabase() {
        val itemTitle = binding.titleEditText.text.toString().trim()
        if (itemTitle.isEmpty()) {
            binding.titleTextField.error = "* Required Field"
            return
        }
        binding.titleTextField.error = null

        var itemDescription: String? = binding.descriptionEditText.text.toString().trim()
        if (itemDescription?.isEmpty() == true) {
            itemDescription = null
        }
        val itemPriority = when (binding.radioGroup.checkedRadioButtonId) {
            R.id.radioButtonLow -> 1
            R.id.radioButtonMedium -> 2
            R.id.radioButtonHigh -> 3
            else -> 0
        }

        if (isInEditMode) {
            val itemEntity = selectedItemEntity!!.copy(
                title = itemTitle,
                description = itemDescription,
                priority = itemPriority
            )
            sharedViewModel.updateItem(itemEntity)
            binding.apply {
                titleEditText.requestFocus()
                mainActivity.showKeyboard(requireView())
            }
            Toast.makeText(requireContext(), "Update Item!", Toast.LENGTH_SHORT).show()
            return
        }

        val itemEntity = DataItem.ItemEntity(
            UUID.randomUUID().toString(),
            itemTitle,
            itemDescription,
            itemPriority,
            System.currentTimeMillis()
        )
        sharedViewModel.insertItem(itemEntity)
    }
}