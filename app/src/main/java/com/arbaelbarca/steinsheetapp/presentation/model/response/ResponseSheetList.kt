package com.arbaelbarca.steinsheetapp.presentation.model.response


import kotlinx.parcelize.Parcelize
import android.os.Parcelable

class ResponseSheetList : ArrayList<ResponseSheetList.ResponseSheetListItem>(){
    @Parcelize
    data class ResponseSheetListItem(
        val area_kota: String?,
        val area_provinsi: String?,
        val komoditas: String?,
        val price: String?,
        val size: String?,
        val tgl_parsed: String?,
        val timestamp: String?,
        val uuid: String?
    ) : Parcelable
}