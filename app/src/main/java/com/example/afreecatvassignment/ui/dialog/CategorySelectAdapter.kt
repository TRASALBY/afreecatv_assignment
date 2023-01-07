package com.example.afreecatvassignment.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ItemCategoryCheckboxBindingImpl
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel

class CategorySelectAdapter(
    private val categoryChangeListener: CategoryChangeListener
) :
    ListAdapter<BroadCategoryUiModel, CategorySelectAdapter.CategorySelectViewHolder>(diffUtil) {

    interface CategoryChangeListener {
        fun onCategoryChangeListener(category: BroadCategoryUiModel, checked: Boolean)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySelectViewHolder {
        return CategorySelectViewHolder(
            categoryChangeListener,
            parent
        )
    }

    override fun onBindViewHolder(holder: CategorySelectViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class CategorySelectViewHolder(
        categoryChangeListener: CategoryChangeListener,
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_category_checkbox, parent, false)
    ) {
        private val binding = ItemCategoryCheckboxBindingImpl.bind(itemView)
        private lateinit var broadCategoryUiModel: BroadCategoryUiModel

        init {
            binding.cbCategory.setOnClickListener {
                categoryChangeListener.onCategoryChangeListener(
                    broadCategoryUiModel,
                    binding.cbCategory.isChecked
                )
            }
        }

        fun bind(broadCategoryUiModel: BroadCategoryUiModel) {
            this.broadCategoryUiModel = broadCategoryUiModel
            binding.category = broadCategoryUiModel
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BroadCategoryUiModel>() {
            override fun areItemsTheSame(
                oldItem: BroadCategoryUiModel,
                newItem: BroadCategoryUiModel
            ): Boolean {
                return oldItem.categoryNumber == newItem.categoryNumber
            }

            override fun areContentsTheSame(
                oldItem: BroadCategoryUiModel,
                newItem: BroadCategoryUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}