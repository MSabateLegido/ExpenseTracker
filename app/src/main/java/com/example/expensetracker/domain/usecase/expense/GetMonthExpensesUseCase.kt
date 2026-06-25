package com.example.expensetracker.domain.usecase.expense

import com.example.expensetracker.data.repository.ExpenseRepository
import com.example.expensetracker.domain.model.expense.ExpenseGroup
import com.example.expensetracker.domain.model.expense.ExpenseGroupBy
import com.example.expensetracker.domain.model.expense.ExpenseGroupKey.*
import com.example.expensetracker.domain.model.expense.ExpenseOrderBy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.YearMonth
import javax.inject.Inject
import kotlin.collections.groupBy

class GetMonthExpensesUseCase @Inject constructor(
    private val expenseRepository: ExpenseRepository
) {
    operator fun invoke(
        yearMonth: YearMonth,
        groupBy: ExpenseGroupBy,
        orderBy: ExpenseOrderBy
    ): Flow<List<ExpenseGroup>> {

        val startOfMonth = yearMonth.atDay(1).toEpochDay()
        val endOfMonth = yearMonth.atEndOfMonth().toEpochDay()

        return expenseRepository
            .getMonthExpenses(startOfMonth, endOfMonth)
            .map { expenses ->

                val grouped = when (groupBy) {
                    ExpenseGroupBy.Day ->
                        expenses.groupBy { expense ->
                            Day(expense.date)
                        }

                    ExpenseGroupBy.Subcategory ->
                        expenses.groupBy { expense ->
                            Subcategory(expense.subcategory)
                        }
                }

                val groups = grouped.map { (key, expensesInGroup) ->
                    ExpenseGroup(
                        key = key,
                        expenses = expensesInGroup,
                        total = expensesInGroup.sumOf { it.amount }
                    )
                }

                sortGroups(groups, orderBy)
            }
    }

    private fun sortGroups(
        groups: List<ExpenseGroup>,
        orderBy: ExpenseOrderBy
    ): List<ExpenseGroup> {
        return when (orderBy) {

            is ExpenseOrderBy.Date -> {
                val sorted = groups.sortedBy {
                    (it.key as? Day)?.date
                }

                if (orderBy.ascending) sorted else sorted.reversed()
            }

            is ExpenseOrderBy.Total -> {
                val sorted = groups.sortedBy { it.total }

                if (orderBy.ascending) sorted else sorted.reversed()
            }

            is ExpenseOrderBy.Alphabetical -> {
                val sorted = groups.sortedBy {
                    when (val key = it.key) {
                        is Subcategory -> key.subcategory.name
                        is Day -> key.date.toString()
                    }
                }

                if (orderBy.ascending) sorted else sorted.reversed()
            }
        }
    }
}