package com.couplesdating.couplet.ui.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.ViewSettingsItemBinding
import com.couplesdating.couplet.ui.profile.model.SettingsItem
import com.couplesdating.couplet.ui.profile.model.SettingsItemType

class SettingsAdapter(
    private val onClick: (type: SettingsItemType) -> Unit
) : ListAdapter<SettingsItem, SettingsAdapter.SettingsViewHolder>(diffUtil) {
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

            when (adapterPosition) {
                itemCount - 1 -> {
                    container.background = ContextCompat.getDrawable(
                        container.context,
                        R.drawable.background_rounded_20_bottom_ripple
                    )
                }
                0 -> {
                    container.background = ContextCompat.getDrawable(
                        container.context,
                        R.drawable.background_rounded_20_top_ripple
                    )
                }
                else -> {
                    container.background =
                        ContextCompat.getDrawable(container.context, R.drawable.ripple_red)
                }
            }
            binding.container.setOnClickListener {
                onClick(settings.type)
            }
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