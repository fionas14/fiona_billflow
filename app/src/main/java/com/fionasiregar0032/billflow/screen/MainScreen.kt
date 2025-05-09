package com.fionasiregar0032.billflow.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fionasiregar0032.billflow.model.Item
import com.fionasiregar0032.billflow.model.PersonWithItems
import com.fionasiregar0032.billflow.ui.theme.BillflowTheme
import com.fionasiregar0032.billflow.viewmodel.BillViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BillViewModel = viewModel()) {
    var baseTotal by remember { mutableStateOf("") }
    var discountPercent by remember { mutableStateOf("") }
    var servicePercent by remember { mutableStateOf("") }
    var numberOfPeople by remember { mutableStateOf("") }
    var numberOfItems by remember { mutableStateOf("") }

    var namesOfPeople by remember { mutableStateOf<List<String>>(emptyList()) }
    var itemNames by remember { mutableStateOf<List<String>>(emptyList()) }
    var itemPrices by remember { mutableStateOf<List<String>>(emptyList()) }

    var validationMessage by remember { mutableStateOf("") }
    var showForm by remember { mutableStateOf(true) }

    Scaffold(
        containerColor = Color(0xFFFFF5E1),
        topBar = {
            TopAppBar(
                title = { Text("Bill Flow") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF800000),
                    titleContentColor = Color.White,
                )
            )
        }
    ) { innerPadding ->
        Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
        ) {
            if (showForm) {

                OutlinedTextField(
                    value = baseTotal,
                    onValueChange = { baseTotal = it },
                    label = { Text("Total Harga Awal (Rp)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = discountPercent,
                    onValueChange = { discountPercent = it },
                    label = { Text("Diskon (%)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = servicePercent,
                    onValueChange = { servicePercent = it },
                    label = { Text("Biaya Layanan (%)") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = numberOfPeople,
                    onValueChange = {
                        numberOfPeople = it
                        namesOfPeople = List(it.toIntOrNull() ?: 0) { "" }
                    },
                    label = { Text("Jumlah Orang") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                namesOfPeople.forEachIndexed { index, name ->
                    OutlinedTextField(
                        value = name,
                        onValueChange = { newName ->
                            namesOfPeople = namesOfPeople.toMutableList().apply { set(index, newName) }
                        },
                        label = { Text("Nama Orang ${index + 1}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                OutlinedTextField(
                    value = numberOfItems,
                    onValueChange = {
                        numberOfItems = it
                        val count = it.toIntOrNull() ?: 0
                        itemNames = List(count) { "" }
                        itemPrices = List(count) { "" }
                    },
                    label = { Text("Jumlah Item") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                itemNames.forEachIndexed { index, item ->
                    OutlinedTextField(
                        value = item,
                        onValueChange = { newName ->
                            itemNames = itemNames.toMutableList().apply { set(index, newName) }
                        },
                        label = { Text("Nama Item ${index + 1}") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    OutlinedTextField(
                        value = itemPrices.getOrElse(index) { "" },
                        onValueChange = { newPrice ->
                            itemPrices = itemPrices.toMutableList().apply {
                                if (index < size) set(index, newPrice)
                            }
                        },
                        label = { Text("Harga Item ${index + 1}") },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Button(onClick = {

                    if (baseTotal.isEmpty() || numberOfPeople.isEmpty() || numberOfItems.isEmpty()) {
                        validationMessage = "Semua field harus diisi."
                        return@Button
                    }

                    if (baseTotal.toDoubleOrNull() == null) {
                        validationMessage = "Total harga awal harus berupa angka."
                        return@Button
                    }

                    if (numberOfPeople.toIntOrNull() == null || numberOfPeople.toInt() <= 0) {
                        validationMessage = "Jumlah orang harus valid."
                        return@Button
                    }

                    if (numberOfItems.toIntOrNull() == null || numberOfItems.toInt() <= 0) {
                        validationMessage = "Jumlah item harus valid."
                        return@Button
                    }

                    if (itemNames.any { it.isBlank() }) {
                        validationMessage = "Nama item tidak boleh kosong."
                        return@Button
                    }

                    if (itemPrices.any { it.isBlank() || it.toDoubleOrNull() == null }) {
                        validationMessage = "Harga item harus berupa angka."
                        return@Button
                    }

                    val total = baseTotal.toDoubleOrNull() ?: 0.0
                    val discount = discountPercent.toDoubleOrNull() ?: 0.0
                    val service = servicePercent.toDoubleOrNull() ?: 0.0

                    val afterDiscount = total - (discount / 100 * total)
                    val finalTotal = afterDiscount + (service / 100 * afterDiscount)

                    namesOfPeople.forEach { name ->
                        val person = PersonWithItems(name)
                        itemNames.forEachIndexed { idx, itemName ->
                            val price = itemPrices[idx].toDoubleOrNull() ?: 0.0
                            person.items.add(Item(itemName, price))
                        }
                        viewModel.addPerson(person)
                    }

                    validationMessage = ""
                    showForm = false
                }) {
                    Text("Split Bill")
                }

                if (validationMessage.isNotEmpty()) {
                    Text(validationMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            } else {

                val totalHarga = viewModel.people.flatMap { it.items }.sumOf { it.price }
                val perPerson = if (viewModel.people.isNotEmpty()) totalHarga / viewModel.people.size else 0.0

                Text("Total Harga Final: Rp $totalHarga")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Total Orang: ${viewModel.people.size}")
                Spacer(modifier = Modifier.height(8.dp))
                Text("Per Orang: Rp ${"%.2f".format(perPerson)}")

                Spacer(modifier = Modifier.height(16.dp))
                OutlinedButton(onClick = {
                    baseTotal = ""
                    discountPercent = ""
                    servicePercent = ""
                    numberOfPeople = ""
                    numberOfItems = ""
                    namesOfPeople = emptyList()
                    itemNames = emptyList()
                    itemPrices = emptyList()
                    viewModel.clearPeople()
                    showForm = true
                }) {
                    Text("Reset")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    BillflowTheme {
        MainScreen()
    }
}
