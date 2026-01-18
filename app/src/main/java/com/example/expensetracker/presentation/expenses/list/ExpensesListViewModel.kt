package com.example.expensetracker.presentation.expenses.list

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.example.expensetracker.domain.model.Category
import com.example.expensetracker.domain.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import java.time.YearMonth

class ExpensesListViewModel : ViewModel() {
    val foodCategory = Category(
        id = 0,
        name = "Menjar",
        color = Color.Green
    )

    val transportCategory = Category(
        id = 1,
        name = "Transport",
        color = Color.Blue
    )

    val leisureCategory = Category(
        id = 2,
        name = "Oci",
        color = Color.Magenta
    )
    val fakeExpensesListState = ExpensesListState(
        months = listOf(

            // ===== GENER 2026 =====
            ExpensesMonth(
                month = YearMonth.of(2026, 1),
                total = "124,15 €",
                expenses = listOf(
                    Expense(
                        1,
                        "Cafè",
                        "2,50 €",
                        foodCategory,
                        LocalDate.of(2026, 1, 3)
                    ),
                    Expense(
                        2,
                        "Metro",
                        "1,65 €",
                        transportCategory,
                        LocalDate.of(2026, 1, 5)
                    ),
                    Expense(
                        3,
                        "Sopar",
                        "22,00 €",
                        foodCategory,
                        LocalDate.of(2026, 1, 10)
                    ),
                    Expense(
                        4,
                        "Cinema",
                        "9,50 €",
                        leisureCategory,
                        LocalDate.of(2026, 1, 18)
                    ),
                    Expense(
                        5,
                        "Dinar feina",
                        "88,50 €",
                        foodCategory,
                        LocalDate.of(2026, 1, 22)
                    )
                )
            ),

            // ===== FEBRER 2026 =====
            ExpensesMonth(
                month = YearMonth.of(2026, 2),
                total = "76,40 €",
                expenses = listOf(
                    Expense(
                        6,
                        "Esmorzar",
                        "3,20 €",
                        foodCategory,
                        LocalDate.of(2026, 2, 2)
                    ),
                    Expense(
                        7,
                        "Bus",
                        "2,10 €",
                        transportCategory,
                        LocalDate.of(2026, 2, 4)
                    ),
                    Expense(
                        8,
                        "Menjar fora",
                        "18,90 €",
                        foodCategory,
                        LocalDate.of(2026, 2, 9)
                    ),
                    Expense(
                        9,
                        "Subscripció música",
                        "9,99 €",
                        leisureCategory,
                        LocalDate.of(2026, 2, 15)
                    ),
                    Expense(
                        10,
                        "Taxi",
                        "42,21 €",
                        transportCategory,
                        LocalDate.of(2026, 2, 21)
                    )
                )
            ),

            // ===== MARÇ 2026 =====
            ExpensesMonth(
                month = YearMonth.of(2026, 3),
                total = "53,85 €",
                expenses = listOf(
                    Expense(
                        11,
                        "Cafè",
                        "2,50 €",
                        foodCategory,
                        LocalDate.of(2026, 3, 1)
                    ),
                    Expense(
                        12,
                        "Metro",
                        "1,65 €",
                        transportCategory,
                        LocalDate.of(2026, 3, 3)
                    ),
                    Expense(
                        13,
                        "Hamburguesa",
                        "11,70 €",
                        foodCategory,
                        LocalDate.of(2026, 3, 8)
                    ),
                    Expense(
                        14,
                        "Cerveses",
                        "15,00 €",
                        leisureCategory,
                        LocalDate.of(2026, 3, 14)
                    ),
                    Expense(
                        15,
                        "Dinar cap de setmana",
                        "23,00 €",
                        foodCategory,
                        LocalDate.of(2026, 3, 20)
                    )
                )
            )
        )
    )

    private val _state = MutableStateFlow(fakeExpensesListState)
    val state: StateFlow<ExpensesListState> = _state

}
