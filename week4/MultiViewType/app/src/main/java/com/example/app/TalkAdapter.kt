package com.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.ItemLeftTalkBinding
import com.example.app.databinding.ItemRightTalkBinding

class TalkAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val lst = mutableListOf<Talk>()
    val LEFT_TALK = 0
    val RIGHT_TALK = 1
    lateinit var itemLeftTalkBinding: ItemLeftTalkBinding
    lateinit var itemRightTalkBinding: ItemRightTalkBinding


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            LEFT_TALK -> {
                itemLeftTalkBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_left_talk,
                    parent,
                    false
                )
                LeftViewHolder(itemLeftTalkBinding)
            }

            RIGHT_TALK -> {
                itemRightTalkBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_right_talk,
                    parent,
                    false
                )
                RightViewHolder(itemRightTalkBinding)
            }

            else -> {
                throw RuntimeException("알 수 없는 viewtype error")
            }
        }
    }

    override fun getItemCount(): Int {
        return lst.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is LeftViewHolder) {
            holder.binding.leftTalk.text = lst[position].talkContent
        } else if (holder is RightViewHolder) {
            holder.binding.rightTalk.text = lst[position].talkContent
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (lst[position].type == "left") LEFT_TALK else RIGHT_TALK
    }


    inner class LeftViewHolder(val binding: ItemLeftTalkBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class RightViewHolder(val binding: ItemRightTalkBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    fun addItem(talk: Talk) {
        lst.add(talk)
    }
}