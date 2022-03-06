package com.arbaelbarca.steinsheetapp.data.apiservice

import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import retrofit2.http.GET

interface ApiServices {

    @GET("list")
    suspend fun getListSheet(

    ): List<ResponseSheetList.ResponseSheetListItem>
}
