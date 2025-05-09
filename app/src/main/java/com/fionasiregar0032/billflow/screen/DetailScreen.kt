package com.fionasiregar0032.billflow.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fionasiregar0032.billflow.viewmodel.BillViewModel

@Composable
fun DetailScreen(viewModel: BillViewModel) {
    var name by remember { mutableStateOf("") }
    var total by remember { mutableStateOf("") }
    var people by remember { mutableStateOf("") }

    var errorMsg by remember { mutableStateOf("") }
    var successMsg by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Input Bill Data", style = MaterialTheme.typography.titleLarge)


        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Names") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = total,
            onValueChange = { total = it },
            label = { Text("Total Amount") },
            modifier = Modifier.fillMaxWidth()
        )


        OutlinedTextField(
            value = people,
            onValueChange = { people = it },
            label = { Text("Number of People") },
            modifier = Modifier.fillMaxWidth()
        )


        Button(
            onClick = {
                viewModel.saveBill(
                    name,
                    total,
                    people,
                    onSuccess = {
                        successMsg = "Bill saved successfully!"
                        errorMsg = ""
                        name = ""
                        total = ""
                        people = ""
                    },
                    onError = {
                        errorMsg = it
                        successMsg = ""
                    }
                )
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Save")
        }

        if (errorMsg.isNotEmpty()) {
            Text(errorMsg, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }

        if (successMsg.isNotEmpty()) {
            Text(successMsg, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
