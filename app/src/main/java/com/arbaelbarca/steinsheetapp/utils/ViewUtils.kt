package com.arbaelbarca.steinsheetapp.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun showView(view: View) {
    view.visibility = View.VISIBLE
}


fun hideView(view: View) {
    view.visibility = View.GONE
}

fun setRvAdapterVertikalDefault(
    recyclerView: RecyclerView,
    adapterDefault: RecyclerView.Adapter<*>
) {
    recyclerView.apply {
        adapter = adapterDefault
        layoutManager = LinearLayoutManager(context)
        hasFixedSize()
    }
}

fun showToast(message: String?, context: Context) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
