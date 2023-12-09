package com.napa.foodstore.presentation.feature.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.napa.foodstore.databinding.ItemCategoriesMenuBinding
import com.napa.foodstore.model.Category

class AdapterListCategories(private val itemClick: (Category) -> Unit) :
    RecyclerView.Adapter<AdapterListCategories.MenuItemCategoriesViewHolder>() {

    private val dataDiffer =
        AsyncListDiffer(
            this,
            object : DiffUtil.ItemCallback<Category>() {
                override fun areItemsTheSame(
                    oldItem: Category,
                    newItem: Category
                ): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areContentsTheSame(
                    oldItem: Category,
                    newItem: Category
                ): Boolean {
                    return oldItem.name == newItem.name
                }
            }
        )

    fun setData(data: List<Category>) {
        dataDiffer.submitList(data)
        notifyItemChanged(0, data.size)
    }

    fun submitData(data: List<Category>) {
        dataDiffer.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, iewType: Int): MenuItemCategoriesViewHolder {
        val binding =
            ItemCategoriesMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemCategoriesViewHolder(binding, itemClick)
    }

    override fun getItemCount(): Int = dataDiffer.currentList.size

    override fun onBindViewHolder(holder: MenuItemCategoriesViewHolder, position: Int) {
        holder.bind(dataDiffer.currentList[position])
    }

    class MenuItemCategoriesViewHolder(
        private val binding: ItemCategoriesMenuBinding,
        val itemClick: (Category) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category) {
            with(item) {
                binding.ivIconCategoriesMenu.load(item.imgUrl) {
                    crossfade(true)
                }
                binding.tvNameMenu.text = item.name
                itemView.setOnClickListener { itemClick(this) }
            }
        }
    }
}
