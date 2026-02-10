package com.example.expensetracker.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.expensetracker.data.local.entity.ExpenseEntity
import com.example.expensetracker.data.local.entity.ExpenseWithSubcategory
import com.example.expensetracker.domain.model.month.MonthData
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {

    @Insert
    suspend fun insert(expense: ExpenseEntity)
    @Update
    suspend fun update(expense: ExpenseEntity)

    @Delete
    suspend fun delete(expense: ExpenseEntity)

    @Query("""
        SELECT * FROM expenses
        ORDER BY date DESC
    """)
    fun getAllExpenses(): Flow<List<ExpenseWithSubcategory>>

    @Query("""
        SELECT * FROM expenses
        WHERE id = :id
    """)
    fun getById(id: Long): ExpenseEntity?

    @Query("""
        SELECT COUNT(*) > 0 FROM expenses
        WHERE subcategoryId = :subcategoryId
    """)
    fun hasExpenses(subcategoryId: Long): Boolean

    @Query("""
        UPDATE expenses
        SET subcategoryId = :toSubcategoryId
        WHERE subcategoryId = :fromSubcategoryId
    """)
    fun moveExpenses(fromSubcategoryId: Long, toSubcategoryId: Long)

    @Query("""
        SELECT
            strftime('%Y', date(date * 86400, 'unixepoch')) AS year,
            strftime('%m', date(date * 86400, 'unixepoch')) AS month,
            SUM(amount) AS total
        FROM expenses
        GROUP BY year, month
        ORDER BY year DESC, month DESC

    """)
    fun getAllMonthData(): Flow<List<MonthData>>
}
