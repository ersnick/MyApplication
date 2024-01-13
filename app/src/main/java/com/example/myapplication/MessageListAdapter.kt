package com.example.myapplication

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import android.view.inputmethod.InputBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemAnimator.ItemHolderInfo
import com.example.myapplication.databinding.MessagesListBinding
import java.text.FieldPosition

class MessageAdapter : ListAdapter<Message, MessageAdapter.MessageHolder>(MessageComparator()) {

    class MessageHolder(private val binding: MessagesListBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(message1: Message) = with(binding){
            message.text = message1.messageText
            role.text = message1.role
        }
        companion object{
            fun create(parent: ViewGroup): MessageHolder{
                return MessageHolder(MessagesListBinding
                    .inflate(LayoutInflater.from(parent.context), parent, false))
            }
        }
    }

    class MessageComparator : DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder{
        return MessageHolder.create(parent)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int){
        holder.bind(getItem(position))
    }
}