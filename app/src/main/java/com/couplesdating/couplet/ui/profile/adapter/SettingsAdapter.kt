package com.couplesdating.couplet.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.couplesdating.couplet.databinding.ViewSettingsItemBinding
import com.couplesdating.couplet.ui.profile.model.SettingsItem

class SettingsAdapter : ListAdapter<SettingsItem, SettingsAdapter.SettingsViewHolder>(diffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewSettingsItemBinding.inflate(layoutInflater, parent, false)
        return SettingsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.setViews(getItem(position))
    }

    inner class SettingsViewHolder(
        private val binding: ViewSettingsItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun setViews(settings: SettingsItem) = with(binding) {
            description.text = settings.description
            icon.setImageResource(settings.icon)
        }
    }

    private companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<SettingsItem>() {
            override fun areItemsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: SettingsItem, newItem: SettingsItem): Boolean {
                return oldItem.icon == newItem.icon &&
                        oldItem.description == newItem.description
            }
        }
    }
}