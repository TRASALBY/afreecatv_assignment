package com.example.afreecatvassignment.ui.broadlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.afreecatvassignment.R
import com.example.afreecatvassignment.databinding.ItemBroadInfoBinding
import com.example.afreecatvassignment.ui.broadlist.BroadListFragmentDirections
import com.example.afreecatvassignment.ui.model.BroadUiModel

class BroadPagingAdapter() :
    PagingDataAdapter<BroadUiModel, BroadPagingAdapter.BroadViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BroadViewHolder {
        return BroadViewHolder(parent)
    }

    override fun onBindViewHolder(holder: BroadViewHolder, position: Int) {
        holder.bind(getItem(position) ?: return)
    }

    class BroadViewHolder(
        parent: ViewGroup
    ) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_broad_info, parent, false)
    ) {
        private val binding = ItemBroadInfoBinding.bind(itemView)
        private lateinit var broadUiModel: BroadUiModel

        init {
            itemView.setOnClickListener {
                val navController = itemView.findNavController()
                navController.navigate(
                    BroadListFragmentDirections.actionBroadListFragmentToBroadDetailFragment(broadUiModel)
                )
            }
        }

        fun bind(broadUiModel: BroadUiModel) {
            this.broadUiModel = broadUiModel
            binding.broad = broadUiModel
        }
    }

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<BroadUiModel>() {
            override fun areItemsTheSame(oldItem: BroadUiModel, newItem: BroadUiModel): Boolean {
                return oldItem.broadNumber == newItem.broadNumber
            }

            override fun areContentsTheSame(oldItem: BroadUiModel, newItem: BroadUiModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}