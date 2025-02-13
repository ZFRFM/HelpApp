package ru.faimizufarov.simbirtraining.java.presentation.ui.screens.news_filter.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.faimizufarov.data.models.CategoryFilterItem
import ru.faimizufarov.simbirtraining.databinding.ItemNewsFilterFragmentBinding

class CategoryFilterAdapter(
    private val onFilterClick: (CategoryFilterItem) -> Unit,
) : ListAdapter<CategoryFilterItem, CategoryFilterViewHolder>
    (CategoryFilterItem.ItemCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CategoryFilterViewHolder {
        val itemBinding =
            ItemNewsFilterFragmentBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )

        return CategoryFilterViewHolder(
            itemBinding = itemBinding,
            onItemClick = { position -> onFilterClick(getItem(position)) },
        )
    }

    override fun onBindViewHolder(
        holder: CategoryFilterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
