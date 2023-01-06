package com.example.afreecatvassignment.ui.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ItemCategoryCheckboxBindingImpl
import com.example.afreecatvassignment.ui.model.BroadCategoryUiModel

class CategorySelectAdapter :
    ListAdapter<BroadCategoryUiModel, CategorySelectAdapter.CategorySelectViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategorySelectViewHolder {
        return CategorySelectViewHolder(parent)
    }

    override fun onBindViewHolder(holder: CategorySelectViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class CategorySelectViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_category_checkbox, parent, false)
    ) {
        private val binding = ItemCategoryCheckboxBindingImpl.bind(itemView)

        fun bind(broadCategoryUiModel: BroadCategoryUiModel) {
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