package com.joaopedro.inventarioredes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joaopedro.inventarioredes.viewmodel.EquipamentoViewModel

/** TELA 3: detalhes de um equipamento, com opções de editar e excluir. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalhesScreen(
    viewModel: EquipamentoViewModel,
    idEquipamento: Int,
    aoVoltar: () -> Unit,
    aoEditar: () -> Unit
) {
    val lista by viewModel.equipamentos.collectAsState()
    val equipamento = lista.find { it.id == idEquipamento }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes") },
                navigationIcon = {
                    IconButton(onClick = aoVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                actions = {
                    if (equipamento != null) {
                        IconButton(onClick = {
                            viewModel.excluir(equipamento)
                            aoVoltar()
                        }) {
                            Icon(Icons.Default.Delete, contentDescription = "Excluir")
                        }
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            if (equipamento == null) {
                Text("Equipamento não encontrado.")
                return@Column
            }

            Text(equipamento.nome, style = MaterialTheme.typography.headlineSmall)

            LinhaDetalhe("Tipo", equipamento.tipo)
            LinhaDetalhe("Endereço IP", equipamento.enderecoIp.ifBlank { "—" })
            LinhaDetalhe("Localização", equipamento.localizacao.ifBlank { "—" })
            LinhaDetalhe("Observações", equipamento.observacoes.ifBlank { "—" })

            Button(
                onClick = aoEditar,
                modifier = Modifier.fillMaxWidth().padding(top = 32.dp)
            ) {
                Text("Editar")
            }
            OutlinedButton(
                onClick = aoVoltar,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text("Voltar para a lista")
            }
        }
    }
}

@Composable
private fun LinhaDetalhe(rotulo: String, valor: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
        Column {
            Text(rotulo, style = MaterialTheme.typography.labelMedium)
            Text(valor, style = MaterialTheme.typography.bodyLarge)
        }
    }
}
