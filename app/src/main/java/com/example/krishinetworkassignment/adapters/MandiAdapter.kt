package com.example.krishinetworkassignment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.krishinetworkassignment.R
import com.example.krishinetworkassignment.dataClasses.OtherMandiItem
import com.example.krishinetworkassignment.databinding.OtherMandiItemViewBinding

class MandiAdapter(private var list: List<OtherMandiItem>) :
    RecyclerView.Adapter<MandiAdapter.MandiViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MandiViewHolder {
        return MandiViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.other_mandi_item_view,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MandiViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setContent(list: List<OtherMandiItem>) {
        this.list = list
        notifyDataSetChanged()
    }

    class MandiViewHolder(private val binding: OtherMandiItemViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val context = binding.root.context
        val colors = listOf(
            context.getColor(R.color.list_color_1),
            context.getColor(R.color.list_color_2),
            context.getColor(R.color.list_color_3),
            context.getColor(R.color.list_color_4)
        )

        fun bind(item: OtherMandiItem) {
            binding.item = item
            binding.color = colors[adapterPosition % 4]
            binding.executePendingBindings()
        }
    }

}