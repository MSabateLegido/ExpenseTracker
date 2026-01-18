package com.example.expensetracker.data.repository

import com.example.expensetracker.data.local.dao.FavoriteDao
import com.example.expensetracker.data.local.entity.FavoriteExpenseEntity
import kotlinx.coroutines.flow.Flow

class FavoriteRepository(
    private val favoriteDao: FavoriteDao
) {

    fun getFavorites(): Flow<List<FavoriteExpenseEntity>> =
        favoriteDao.getAll()

    suspend fun deleteFavorite(favorite: FavoriteExpenseEntity) {
        favoriteDao.delete(favorite)
    }
}