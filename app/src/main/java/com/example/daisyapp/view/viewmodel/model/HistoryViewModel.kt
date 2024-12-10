package com.example.daisyapp.view.viewmodel.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.daisyapp.data.model.CancerHistory
import com.example.daisyapp.data.repository.HistoryRepository
import kotlinx.coroutines.launch

class HistoryViewModel (private val cancerHistoryRepository: HistoryRepository) :
    ViewModel() {
    fun getAllCancerHistory() = cancerHistoryRepository.getAllCancerHistory()

    fun insertCancerHistory(cancerHistory: CancerHistory) {
        viewModelScope.launch {
            cancerHistoryRepository.insertCancerHistory(cancerHistory)
        }
    }

    fun deleteResultById(id: Int) {
        viewModelScope.launch {
            cancerHistoryRepository.deleteResultById(id)
        }
    }
}