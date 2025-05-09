package com.fionasiregar0032.billflow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.fionasiregar0032.billflow.screen.MainScreen
import com.fionasiregar0032.billflow.ui.theme.BillflowTheme
import com.fionasiregar0032.billflow.viewmodel.BillViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BillflowTheme {
                val viewModel: BillViewModel = viewModel()
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen(viewModel = viewModel)
                }
            }
        }
    }
}
