package com.example.daisyapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class CancerHistory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "label")
    val label: String,

    @ColumnInfo(name = "confidence_score")
    val confidenceScore: String,

    @ColumnInfo(name = "image")
    val image: String
) : Parcelable