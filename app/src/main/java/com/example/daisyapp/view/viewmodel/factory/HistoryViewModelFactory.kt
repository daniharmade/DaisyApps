package com.example.daisyapp.view.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.daisyapp.data.injection.HistoryInjection
import com.example.daisyapp.data.repository.HistoryRepository
import com.example.daisyapp.view.viewmodel.model.HistoryViewModel

class HistoryViewModelFactory(private val cancerHistoryRepository: HistoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(cancerHistoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null
        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(
                    HistoryInjection.provideCancerHistoryRepository(
                        context
                    )
                )
            }.also { instance = it }
    }
}