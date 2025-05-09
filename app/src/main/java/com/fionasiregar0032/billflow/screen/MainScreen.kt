package com.fionasiregar0032.billflow.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fionasiregar0032.billflow.ui.theme.BillflowTheme
import com.fionasiregar0032.billflow.viewmodel.BillViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: BillViewModel) {
    Scaffold(
        containerColor = Color(0xFFFFF5E1),
        topBar = {
            TopAppBar(
                title = { Text(text = "Bill Flow") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color(0xFF800000),
                    titleContentColor = Color.White,
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {  },
                containerColor = Color(0xFF800000)
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Tambah Split Bill",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        HomeScreen(
            viewModel = viewModel,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun HomeScreen(viewModel: BillViewModel, modifier: Modifier = Modifier) {
    var totalAmount by remember { mutableStateOf("") }
    var numberOfPeople by remember { mutableStateOf("") }
    var names by remember { mutableStateOf("") }
    var ppn by remember { mutableStateOf("") }
    var pricePerPerson by remember { mutableStateOf("") }

    var isFormVisible by remember { mutableStateOf(false) }

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
            }) {
                Text("Hitung & Simpan")
            }
        }
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun MainScreenPreview() {
    BillflowTheme {
        MainScreen(viewModel = viewModel())
    }
}
