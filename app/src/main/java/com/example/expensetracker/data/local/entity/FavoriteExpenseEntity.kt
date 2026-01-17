package com.example.expensetracker.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite_expenses",
    foreignKeys = [
        ForeignKey(
            entity = SubcategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["subcategoryId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.RESTRICT
        )
    ],
    indices = [
        Index("subcategoryId"),
        Index(value = ["name"], unique = true)
    ]
)
data class FavoriteExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val amount: Double,
    val subcategoryId: Long,
    val icon: String
)

