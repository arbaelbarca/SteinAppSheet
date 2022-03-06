package com.arbaelbarca.steinsheetapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseCityList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSizeList
import com.arbaelbarca.steinsheetapp.presentation.repository.RepositorySheet
import com.arbaelbarca.steinsheetapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSheet @Inject constructor(val repositorySheet: RepositorySheet) : ViewModel() {

    val stateSheetList = MutableLiveData<UiState<List<ResponseSheetList.ResponseSheetListItem>>>()
    val stateListCity = MutableLiveData<UiState<List<ResponseCityList.ResponseCityListItem>>>()
    val stateListSize = MutableLiveData<UiState<List<ResponseSizeList.ResponseSizeListItem>>>()

    fun observerListSheet() = stateSheetList
    fun observerCityList() = stateListCity
    fun observerSizeList() = stateListSize

    fun getSheetList() {
        stateSheetList.value = UiState.Loading()
        viewModelScope.launch {
            kotlin.runCatching {
                repositorySheet.callApiListSheet()
            }.onSuccess {
                stateSheetList.value = UiState.Success(it)
            }.onFailure {
                stateSheetList.value = UiState.Failure(it)
            }
        }
    }

    fun getCityList() {
        stateListCity.value = UiState.Loading()
        viewModelScope.launch {
            kotlin.runCatching {
                repositorySheet.callApiListCity()
            }.onSuccess {
                stateListCity.value = UiState.Success(it)
            }.onFailure {
                stateListCity.value = UiState.Failure(it)
            }
        }
    }

    fun getSizeList() {
        stateListSize.value = UiState.Loading()
        viewModelScope.launch {
            kotlin.runCatching {
                repositorySheet.callApiListSize()
            }.onSuccess {
                stateListSize.value = UiState.Success(it)
            }.onFailure {
                stateListSize.value = UiState.Failure(it)
            }
        }
    }
}