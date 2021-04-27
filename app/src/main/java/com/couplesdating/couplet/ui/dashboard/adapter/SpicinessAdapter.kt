package com.couplesdating.couplet.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.couplesdating.couplet.R
import com.couplesdating.couplet.databinding.ViewPepperItemBinding
import com.couplesdating.couplet.ui.extensions.isDarkTheme

class SpicinessAdapter(
    private val spiceLevel: Int
) : RecyclerView.Adapter<SpicinessAdapter.SpiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpiceViewHolder {
        val binding = ViewPepperItemBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SpiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SpiceViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = 4

    inner class SpiceViewHolder(
        private val pepperItemBinding: ViewPepperItemBinding
    ) : RecyclerView.ViewHolder(pepperItemBinding.root) {

        fun bind() = with(pepperItemBinding) {
            pepper.setImageResource(getIndicatorImage())
        }

        private fun getIndicatorImage(): Int = with(pepperItemBinding) {
            val itemNumber = adapterPosition + 1
            return if (itemNumber <= spiceLevel) {
                R.drawable.ic_dark_chilli_able
            } else {
                val isDarkTheme = root.context.isDarkTheme()
                if (isDarkTheme) {
                    R.drawable.ic_dark_chilli_disable
                } else {
                    R.drawable.ic_chilli_disable
                }
            }
        }
    }
}