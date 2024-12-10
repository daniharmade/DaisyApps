package com.example.daisyapp.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.daisyapp.data.model.CancerHistory

@Dao
interface HistoryDao {
    @Query("SELECT * FROM CancerHistory ORDER BY id DESC")
    fun getAllCancerHistory(): LiveData<List<CancerHistory>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCancerHistory(cancerHistory: CancerHistory)

    @Query("DELETE FROM CancerHistory WHERE id = :id")
    suspend fun deleteResultById(id: Int)
}