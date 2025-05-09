package com.fionasiregar0032.billflow.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.fionasiregar0032.billflow.viewmodel.BillViewModel

@Composable
fun DetailScreen(
    viewModel: BillViewModel,
    modifier: Modifier = Modifier,
    isFormVisible: Boolean,
    onFormVisibilityChange: (Boolean) -> Unit
    ) {
        var totalAmount by remember { mutableStateOf("") }
        var numberOfPeople by remember { mutableStateOf("") }
        var names by remember { mutableStateOf("") }
        var ppn by remember { mutableStateOf("") }
        var pricePerPerson by remember { mutableStateOf("") }

        val totalAmountFloat = totalAmount.toFloatOrNull() ?: 0f
        val numberOfPeopleInt = numberOfPeople.toIntOrNull() ?: 0
        val ppnFloat = ppn.toFloatOrNull() ?: 0f

        val pricePerPersonCalculated = if (numberOfPeopleInt > 0) {
            val totalWithPpn = totalAmountFloat * (1 + ppnFloat / 100)
            (totalWithPpn / numberOfPeopleInt).toString()
        } else {
            "0"
        }

        pricePerPerson = pricePerPersonCalculated

        Column(modifier = modifier.padding(16.dp)) {
            if (isFormVisible) {
                OutlinedTextField(
                    value = totalAmount,
                    onValueChange = { totalAmount = it },
                    label = { Text("Total Tagihan") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = numberOfPeople,
                    onValueChange = { numberOfPeople = it },
                    label = { Text("Jumlah Orang") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = names,
                    onValueChange = { names = it },
                    label = { Text("Nama-nama (pisah koma)") }
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ppn,
                    onValueChange = { ppn = it },
                    label = { Text("PPN (%)") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Harga per orang: Rp $pricePerPerson", style = MaterialTheme.typography.bodyLarge)

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    // Hitung dan simpan bisa diisi di sini
                    onFormVisibilityChange(false) // Sembunyikan form setelah simpan (opsional)
                }) {
                    Text("Hitung & Simpan")
                }
            }
        }
    }
