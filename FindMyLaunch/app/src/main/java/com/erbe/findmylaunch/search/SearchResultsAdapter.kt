package com.erbe.findmylaunch.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.erbe.findmylaunch.databinding.ItemSearchResultBinding
import com.erbe.findmylaunch.db.Launch

class SearchResultsAdapter(
    private val onResultClicked: (Launch) -> Unit
) : ListAdapter<Launch, LaunchViewHolder>(LaunchDiffer()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LaunchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchResultBinding.inflate(inflater, parent, false)
        return LaunchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LaunchViewHolder, position: Int) {
        holder.bind(getItem(position), onResultClicked)
    }
}

class LaunchViewHolder(
    private val binding: ItemSearchResultBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(launch: Launch, onResultClicked: (Launch) -> Unit) {
        binding.name.text = launch.name
        binding.description.text = launch.details
        binding.root.setOnClickListener {
            onResultClicked.invoke(launch)
        }
    }
}

class LaunchDiffer : DiffUtil.ItemCallback<Launch>() {

    override fun areItemsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Launch, newItem: Launch): Boolean {
        return oldItem == newItem
    }
}