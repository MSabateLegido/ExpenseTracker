package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.local.entity.SubcategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubcategoryDao {

    @Insert
    suspend fun insert(subcategory: SubcategoryEntity)

    @Update
    suspend fun update(subcategory: SubcategoryEntity)

    @Delete
    suspend fun delete(subcategory: SubcategoryEntity)

    @Query("""
        SELECT * FROM subcategories
        WHERE categoryId = :categoryId
        ORDER BY name
    """)
    fun getByCategory(categoryId: Long): Flow<List<SubcategoryEntity>>

}
