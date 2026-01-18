package com.example.expensetracker.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.expensetracker.domain.model.Subcategory

data class SubcategoryWithCategory(
    @Embedded val subcategory: SubcategoryEntity,

    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val category: CategoryEntity
)
fun SubcategoryWithCategory.toUi(): Subcategory =
    Subcategory(
        id = subcategory.id,
        name = subcategory.name,
        color = subcategory.color,
        category = category.toUi()
    )


