package com.arbaelbarca.steinsheetapp.presentation.repository

import com.arbaelbarca.steinsheetapp.data.apiservice.ApiServices
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseCityList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSizeList
import javax.inject.Inject

class RepositorySheet @Inject constructor(val apiServices: ApiServices) {

    suspend fun callApiListSheet(): List<ResponseSheetList.ResponseSheetListItem> {
        return apiServices.getListSheet()
    }

    suspend fun callApiListCity(): List<ResponseCityList.ResponseCityListItem> {
        return apiServices.getListCity()
    }

    suspend fun callApiListSize(): List<ResponseSizeList.ResponseSizeListItem> {
        return apiServices.getListSize()
    }
}