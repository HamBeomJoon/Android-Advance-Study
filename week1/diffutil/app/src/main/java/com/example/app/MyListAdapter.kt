package com.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ViewItemlistBinding
import com.google.android.material.snackbar.Snackbar
import java.util.Collections

class MyListAdapter : ListAdapter<Monster, RecyclerView.ViewHolder>(MyDiffCallback()) {

    inner class MyViewHolder(private val binding: ViewItemlistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Monster) {
            with(binding) {
                tvName.text = "Name: ${data.name}"
                tvLevel.text = "Level: ${data.level}"
                tvStats.text = "HP: ${data.stats[0]} / MP: ${data.stats[1]} / Exp: ${data.stats[2]}"

                vhLayout.setOnClickListener {
                    Snackbar.make(
                        it,
                        "Item $adapterPosition touched!",
                        Snackbar.LENGTH_SHORT
                    )
                        .show()
                }
            }
        }

        fun setAlpha(alpha: Float) {
            with(binding) {
                tvName.alpha = alpha
                tvLevel.alpha = alpha
                tvStats.alpha = alpha
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MyViewHolder(
            ViewItemlistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            val monster = getItem(position) as Monster
            holder.bind(monster)
        }
    }

    fun moveItem(fromPosition: Int, toPosition: Int) {
        val newList = currentList.toMutableList()
        Collections.swap(newList, fromPosition, toPosition)
        submitList(newList)
    }

    fun removeItem(position: Int) {
        val newList = currentList.toMutableList()
        newList.removeAt(position)
        submitList(newList)
    }
}