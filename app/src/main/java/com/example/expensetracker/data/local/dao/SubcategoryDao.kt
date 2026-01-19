package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.local.entity.SubcategoryEntity
import com.example.expensetracker.data.local.entity.SubcategoryWithCategory
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
        DELETE FROM subcategories
        WHERE id = :id
    """)
    fun deleteById(id: Long)

    @Query("""
        SELECT * FROM subcategories
    """)
    fun getAllSubcategories(): Flow<List<SubcategoryWithCategory>>

    @Query("""
        SELECT * FROM subcategories
        WHERE categoryId = :categoryId
        ORDER BY name
    """)
    fun getByCategory(categoryId: Long): Flow<List<SubcategoryEntity>>

    @Query("""
        SELECT COUNT(*) FROM subcategories
        WHERE categoryId = :categoryId
    """)
    fun countByCategory(categoryId: Long): Int

}
