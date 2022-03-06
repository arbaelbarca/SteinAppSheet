package com.arbaelbarca.steinsheetapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.steinsheetapp.databinding.LayoutItemTextFilterBinding
import com.arbaelbarca.steinsheetapp.presentation.listener.OnClickItem
import com.arbaelbarca.steinsheetapp.presentation.model.request.RequestTextFilter
import com.arbaelbarca.steinsheetapp.utils.ViewBindingVH

class AdapterTextFilter(
    val mutableListText: MutableList<RequestTextFilter>,
    val onClickItem: OnClickItem
) : RecyclerView.Adapter<ViewBindingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingVH {
        return ViewBindingVH.create(parent, LayoutItemTextFilterBinding::inflate)
    }

    override fun onBindViewHolder(holder: ViewBindingVH, position: Int) {
        val dataItem = mutableListText[position]
        (holder.binding as LayoutItemTextFilterBinding).apply {
            tvItemTextFilter.text = dataItem.text

            holder.itemView.setOnClickListener {
                onClickItem.clicKItem(position, dataItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return mutableListText.size
    }
}