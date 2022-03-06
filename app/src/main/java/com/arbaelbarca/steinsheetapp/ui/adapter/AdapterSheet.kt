package com.arbaelbarca.steinsheetapp.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.arbaelbarca.steinsheetapp.databinding.LayoutItemSheetBinding
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.utils.ViewBindingVH

class AdapterSheet(
    var mutableListSheet: MutableList<ResponseSheetList.ResponseSheetListItem>
) : RecyclerView.Adapter<ViewBindingVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewBindingVH {
        return ViewBindingVH.create(parent, LayoutItemSheetBinding::inflate)
    }

    override fun onBindViewHolder(holder: ViewBindingVH, position: Int) {
        val dataItem = mutableListSheet[position]
        (holder.binding as LayoutItemSheetBinding).apply {
            tvItemSheetKomodity.text = dataItem.komoditas
            tvItemProvinsiSheet.text = dataItem.area_provinsi
            tvItemCitySheet.text = dataItem.area_kota
            tvItemSizeSheet.text = dataItem.size
            tvItemPriceSheet.text = dataItem.price
        }
    }

    override fun getItemCount(): Int {
        return mutableListSheet.size
    }

    fun searchFilter(listSheet: MutableList<ResponseSheetList.ResponseSheetListItem>) {
        mutableListSheet = listSheet
        notifyDataSetChanged()
    }

}