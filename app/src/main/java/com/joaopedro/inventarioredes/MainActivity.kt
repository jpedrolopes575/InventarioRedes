package com.joaopedro.inventarioredes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.joaopedro.inventarioredes.data.AppDatabase
import com.joaopedro.inventarioredes.data.EquipamentoRepository
import com.joaopedro.inventarioredes.ui.navigation.AppNavigation
import com.joaopedro.inventarioredes.ui.theme.InventarioRedesTheme
import com.joaopedro.inventarioredes.viewmodel.EquipamentoViewModel
import com.joaopedro.inventarioredes.viewmodel.EquipamentoViewModelFactory

/**
 * Ponto de entrada do app: monta banco -> repositório -> ViewModel -> navegação.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InventarioRedesTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val context = LocalContext.current
                    val repositorio = EquipamentoRepository(
                        AppDatabase.getDatabase(context).equipamentoDao()
                    )
                    val viewModel: EquipamentoViewModel = viewModel(
                        factory = EquipamentoViewModelFactory(repositorio)
                    )
                    AppNavigation(viewModel)
                }
            }
        }
    }
}