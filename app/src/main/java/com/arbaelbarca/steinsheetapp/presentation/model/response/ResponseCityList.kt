package com.arbaelbarca.steinsheetapp.presentation.model.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class ResponseCityList : ArrayList<ResponseCityList.ResponseCityListItem>(){
    @Parcelize
    data class ResponseCityListItem(
        val city: String?,
        val province: String?
    ) : Parcelable
}