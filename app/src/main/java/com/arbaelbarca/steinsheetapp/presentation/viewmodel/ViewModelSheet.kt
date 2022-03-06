package com.arbaelbarca.steinsheetapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arbaelbarca.steinsheetapp.presentation.model.response.ResponseSheetList
import com.arbaelbarca.steinsheetapp.presentation.repository.RepositorySheet
import com.arbaelbarca.steinsheetapp.utils.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelSheet @Inject constructor(val repositorySheet: RepositorySheet) : ViewModel() {

    val stateSheetList = MutableLiveData<UiState<List<ResponseSheetList.ResponseSheetListItem>>>()

    fun observerListSheet() = stateSheetList

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
}