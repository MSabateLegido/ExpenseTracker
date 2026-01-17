package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.expensetracker.data.local.entity.FavoriteExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert
    suspend fun insert(favorite: FavoriteExpenseEntity)

    @Delete
    suspend fun delete(favorite: FavoriteExpenseEntity)

    @Query("""
        SELECT * FROM favorite_expenses
        ORDER BY name
    """)
    fun getAll(): Flow<List<FavoriteExpenseEntity>>

}
