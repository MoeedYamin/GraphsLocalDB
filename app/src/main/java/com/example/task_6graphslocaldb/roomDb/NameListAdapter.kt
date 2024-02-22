package com.example.task_6graphslocaldb.roomDb

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task_6graphslocaldb.GraphActivity
import com.example.task_6graphslocaldb.databinding.ItemNodesBinding


class NameListAdapter : RecyclerView.Adapter<NameListAdapter.NameViewHolder>() {
    private var names: List<NameEntity> = emptyList()
    private var onItemLongClickListener: ((NameEntity) -> Unit)? = null

    fun setOnItemLongClickListener(listener: (NameEntity) -> Unit) {
        onItemLongClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        val binding = ItemNodesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NameViewHolder(binding)

    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        holder.bind(names[position])

        holder.itemView.setOnClickListener {
            val nameEntity = names[position]
            val intent = Intent(holder.itemView.context, GraphActivity::class.java)
            intent.putExtra("nameEntityId", nameEntity.id)
            holder.itemView.context.startActivity(intent)
        }

        holder.itemView.setOnLongClickListener {
            val nameEntity = names[position]
            onItemLongClickListener?.invoke(nameEntity)
            true
        }
    }

    override fun getItemCount(): Int {
        return names.size
    }

    fun setNames(names: List<NameEntity>) {
        this.names = names
        notifyDataSetChanged()
    }
    inner class NameViewHolder(private val binding: ItemNodesBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(name: NameEntity) {
            binding.listItem = name
            binding.executePendingBindings()
        }
    }
}