package com.example.daisyapp.data.injection

import android.content.Context
import com.example.daisyapp.data.repository.HistoryRepository
import com.example.daisyapp.data.room.HistoryDatabase

object HistoryInjection {
    fun provideCancerHistoryRepository(context: Context): HistoryRepository {
        val database = HistoryDatabase.getInstance(context)
        val dao = database.cancerHistoryDao()
        return HistoryRepository.getInstance(dao)
    }
}