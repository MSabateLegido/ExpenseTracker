package com.example.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = SubcategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["subcategoryId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("subcategoryId"),
        Index("date")
    ]
)
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String,
    val amount: Double,
    val date: LocalDate = LocalDate.now(),
    val subcategoryId: Long
)




