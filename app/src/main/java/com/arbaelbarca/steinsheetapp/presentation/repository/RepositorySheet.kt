package com.arbaelbarca.steinsheetapp.presentation.repository

import com.arbaelbarca.steinsheetapp.data.apiservice.ApiServices
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import javax.inject.Inject

class RepositorySheet @Inject constructor(val apiServices: ApiServices) {

    suspend fun callApiListSheet():List<ResponseSheetList.ResponseSheetListItem>{
        return apiServices.getListSheet()
    }
}