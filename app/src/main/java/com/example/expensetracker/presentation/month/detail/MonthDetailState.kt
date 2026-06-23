package com.example.expensetracker.presentation.month.detail

import com.example.expensetracker.domain.model.category.CategoryWithChildren
import com.example.expensetracker.domain.model.expense.DayExpenses
import com.example.expensetracker.domain.model.expense.Expense
import com.example.expensetracker.domain.model.expense.ExpenseGroup
import com.example.expensetracker.domain.model.expense.ExpenseGroupBy
import com.example.expensetracker.domain.model.expense.ExpenseOrderBy
import java.time.YearMonth

data class MonthDetailState(
    val expenseGroups: List<ExpenseGroup> = emptyList(),
    val groupBy: ExpenseGroupBy = ExpenseGroupBy.Day,
    val orderBy: ExpenseOrderBy = ExpenseOrderBy.Date(false),
    val yearMonth: YearMonth = YearMonth.now(),
    val selectedExpense: Expense? = null,
    val categories: List<CategoryWithChildren> = emptyList()
)