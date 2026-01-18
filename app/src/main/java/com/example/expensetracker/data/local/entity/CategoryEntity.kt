package com.example.expensetracker.data.local.entity

import androidx.core.graphics.toColor
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.expensetracker.domain.model.Category

@Entity(
    tableName = "categories",
    indices = [
        Index(value = ["name"], unique = true)
    ]
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val color: Int
)
fun CategoryEntity.toUi(): Category =
    Category(
        id = id,
        name = name,
        color = color.toColor()
    )


