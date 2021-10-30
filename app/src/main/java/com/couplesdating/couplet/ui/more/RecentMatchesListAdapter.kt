package com.couplesdating.couplet.ui.more

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.ViewRecentMatchItemBinding

class RecentMatchesListAdapter(
    private val onMatchClicked: (matchId: String) -> Unit
) :
    ListAdapter<RecentMatch, RecentMatchesListAdapter.RecentMatchViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentMatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewRecentMatchItemBinding.inflate(layoutInflater, parent, false)
        return RecentMatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentMatchViewHolder, position: Int) {
        holder.setViews(getItem(position))
    }

    inner class RecentMatchViewHolder(private val binding: ViewRecentMatchItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setViews(recentMatch: RecentMatch) = with(binding) {
            ideaNumber.text =
                root.context.getString(R.string.recent_match_number, recentMatch.index)
            description.text = recentMatch.description
            matchContainer.setOnClickListener {
                onMatchClicked(recentMatch.id)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RecentMatch>() {
            override fun areItemsTheSame(oldItem: RecentMatch, newItem: RecentMatch): Boolean {
                return oldItem.index == newItem.index &&
                        oldItem.description == newItem.description
            }

            override fun areContentsTheSame(oldItem: RecentMatch, newItem: RecentMatch): Boolean {
                return oldItem.description == newItem.description
            }

        }
    }
}