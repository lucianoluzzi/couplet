package com.couplesdating.couplet.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.couplesdating.couplet.databinding.ViewCategoryItemBinding

class CategoryAdapter(
    private val categories: List<CategoryUIModel>
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ViewCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size

    inner class CategoryViewHolder(
        private val itemBinding: ViewCategoryItemBinding
    ) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(category: CategoryUIModel) = with(itemBinding) {
            illustration.setImageResource(category.image)
            title.text = category.title
            description.text = category.description
            premium.isVisible = category.isPremium
            newIdeas.isVisible = category.hasNewIdeas
        }
    }
}