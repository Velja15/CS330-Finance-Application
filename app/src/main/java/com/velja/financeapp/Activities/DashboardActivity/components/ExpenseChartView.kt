package com.velja.financeapp.Activities.DashboardActivity.components

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.velja.financeapp.Domain.ExpenseDomain
import com.velja.financeapp.R

@Composable
fun ExpenseChartView(
    expenses: List<ExpenseDomain>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = androidx.compose.ui.graphics.Color.White
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Expense Breakdown",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(R.color.darkBlue),
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (expenses.isEmpty()) {
                Text(
                    text = "No expenses to display",
                    color = colorResource(R.color.grey),
                    modifier = Modifier.padding(32.dp)
                )
            } else {
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    factory = { context ->
                        PieChart(context).apply {
                            description.isEnabled = false
                            setDrawEntryLabels(true)
                            setEntryLabelColor(Color.BLACK)
                            setEntryLabelTextSize(12f)
                            legend.isEnabled = true
                            legend.textSize = 12f
                            setHoleColor(Color.TRANSPARENT)
                            holeRadius = 40f
                            transparentCircleRadius = 45f
                        }
                    },
                    update = { pieChart ->
                        val groupedExpenses = expenses.groupBy { it.title }
                            .mapValues { entry -> entry.value.sumOf { it.price } }

                        val entries = groupedExpenses.map { (title, total) ->
                            PieEntry(total.toFloat(), title)
                        }

                        val colors = listOf(
                            Color.parseColor("#FF6384"),
                            Color.parseColor("#36A2EB"),
                            Color.parseColor("#FFCE56"),
                            Color.parseColor("#4BC0C0"),
                            Color.parseColor("#9966FF"),
                            Color.parseColor("#FF9F40"),
                            Color.parseColor("#FF6384"),
                            Color.parseColor("#C9CBCF")
                        )

                        val dataSet = PieDataSet(entries, "").apply {
                            setColors(colors)
                            valueTextSize = 14f
                            valueTextColor = Color.WHITE
                            sliceSpace = 3f
                        }

                        pieChart.data = PieData(dataSet)
                        pieChart.animateY(1000)
                        pieChart.invalidate()
                    }
                )
            }
        }
    }
}