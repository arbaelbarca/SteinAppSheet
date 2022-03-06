package com.arbaelbarca.steinsheetapp.data.apiservice

import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseCityList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSizeList
import retrofit2.http.GET

interface ApiServices {

    @GET("list")
    suspend fun getListSheet(

    ): List<ResponseSheetList.ResponseSheetListItem>

    @GET("option_area")
    suspend fun getListCity(

    ): List<ResponseCityList.ResponseCityListItem>

    @GET("option_size")
    suspend fun getListSize(

    ): List<ResponseSizeList.ResponseSizeListItem>
}
