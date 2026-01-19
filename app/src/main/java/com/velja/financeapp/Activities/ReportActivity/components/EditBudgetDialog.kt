package com.velja.financeapp.Activities.ReportActivity.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.velja.financeapp.Domain.BudgetDomain
import com.velja.financeapp.R

@Composable
fun EditBudgetDialog(
    budget: BudgetDomain,
    onDismiss: () -> Unit,
    onConfirm: (BudgetDomain) -> Unit
) {
    var title by remember { mutableStateOf(budget.title) }
    var price by remember { mutableStateOf(budget.price.toString()) }
    var percent by remember { mutableStateOf(budget.percent.toString()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Edit Budget",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.darkBlue)
                )

                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Budget Category") },
                    placeholder = { Text("e.g., Groceries, Rent") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = price,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            price = it
                        }
                    },
                    label = { Text("Monthly Budget") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    prefix = { Text("$") }
                )

                OutlinedTextField(
                    value = percent,
                    onValueChange = {
                        if (it.isEmpty() || it.matches(Regex("^\\d*\\.?\\d*$"))) {
                            val value = it.toDoubleOrNull()
                            if (value == null || value <= 100) {
                                percent = it
                            }
                        }
                    },
                    label = { Text("Percentage of Total Budget") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    suffix = { Text("%") },
                    supportingText = { Text("Enter value between 0-100") }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (title.isNotBlank() && price.isNotBlank() && percent.isNotBlank()) {
                                val updatedBudget = budget.copy(
                                    title = title,
                                    price = price.toDoubleOrNull() ?: budget.price,
                                    percent = percent.toDoubleOrNull() ?: budget.percent
                                )
                                onConfirm(updatedBudget)
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colorResource(R.color.blue)
                        ),
                        enabled = title.isNotBlank() && price.isNotBlank() && percent.isNotBlank()
                    ) {
                        Text("Update")
                    }
                }
            }
        }
    }
}