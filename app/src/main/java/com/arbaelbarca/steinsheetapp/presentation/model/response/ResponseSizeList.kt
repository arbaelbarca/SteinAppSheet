package com.arbaelbarca.steinsheetapp.presentation.model.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class ResponseSizeList : ArrayList<ResponseSizeList.ResponseSizeListItem>(){
    @Parcelize
    data class ResponseSizeListItem(
        val size: String?
    ) : Parcelable
}