package com.joaopedro.inventarioredes.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.joaopedro.inventarioredes.data.Equipamento
import com.joaopedro.inventarioredes.viewmodel.EquipamentoViewModel

/** TELA 1: lista dos equipamentos salvos no banco. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListaScreen(
    viewModel: EquipamentoViewModel,
    aoClicarNovo: () -> Unit,
    aoClicarItem: (Int) -> Unit
) {
    // A tela apenas OBSERVA o estado exposto pelo ViewModel.
    val equipamentos: List<Equipamento> by viewModel.equipamentos.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Inventário de Redes") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = aoClicarNovo) {
                Icon(Icons.Default.Add, contentDescription = "Adicionar equipamento")
            }
        }
    ) { padding ->
        if (equipamentos.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding).padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Nenhum equipamento cadastrado.\nToque no botão + para adicionar o primeiro.",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(equipamentos, key = { it.id }) { equipamento ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { aoClicarItem(equipamento.id) }
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(equipamento.nome, style = MaterialTheme.typography.titleMedium)
                            Text(
                                "${equipamento.tipo} • ${equipamento.enderecoIp}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                equipamento.localizacao,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
}
