package com.couplesdating.couplet.ui.matches.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.couplesdating.couplet.databinding.ViewMatchItemBinding
import com.couplesdating.couplet.domain.model.Match

class MatchAdapter(
    private val matches: List<Match>,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<MatchAdapter.MatchViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ViewMatchItemBinding.inflate(layoutInflater, parent, false)
        return MatchViewHolder(binding) {
            onItemClick(it)
        }
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.bind(matches[position])
    }

    override fun getItemCount() = matches.size

    class MatchViewHolder(
        private val itemBinding: ViewMatchItemBinding,
        private val onItemClick: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(match: Match) = with(itemBinding) {
            ideaNumber.text = "$adapterPosition."
            description.text = match.idea.description
            itemView.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }
    }
}