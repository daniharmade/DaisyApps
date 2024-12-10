package com.example.daisyapp.data.repository

import androidx.lifecycle.LiveData
import com.example.daisyapp.data.model.CancerHistory
import com.example.daisyapp.data.room.HistoryDao

class HistoryRepository private constructor(
    private val cancerHistoryDao: HistoryDao,
) {
    fun getAllCancerHistory(): LiveData<List<CancerHistory>> {
        return cancerHistoryDao.getAllCancerHistory()
    }

    suspend fun insertCancerHistory(cancerHistory: CancerHistory) {
        return cancerHistoryDao.insertCancerHistory(cancerHistory)
    }

    suspend fun deleteResultById(id: Int) {
        cancerHistoryDao.deleteResultById(id)
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            cancerHistoryDao: HistoryDao,
        ): HistoryRepository = instance ?: synchronized(this) {
            instance ?: HistoryRepository(cancerHistoryDao).also {
                instance = it
            }
        }
    }
}