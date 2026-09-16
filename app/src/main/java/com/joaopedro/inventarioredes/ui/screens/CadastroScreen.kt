package com.joaopedro.inventarioredes.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.joaopedro.inventarioredes.data.Equipamento
import com.joaopedro.inventarioredes.viewmodel.EquipamentoViewModel

/** TELA 2: cadastro de um novo equipamento OU edição de um existente. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CadastroScreen(
    viewModel: EquipamentoViewModel,
    idEquipamento: Int,
    aoVoltar: () -> Unit
) {
    val existente = remember(idEquipamento) {
        if (idEquipamento != 0) viewModel.buscarPorId(idEquipamento) else null
    }

    var nome by remember { mutableStateOf(existente?.nome ?: "") }
    var tipo by remember { mutableStateOf(existente?.tipo ?: "") }
    var ip by remember { mutableStateOf(existente?.enderecoIp ?: "") }
    var local by remember { mutableStateOf(existente?.localizacao ?: "") }
    var obs by remember { mutableStateOf(existente?.observacoes ?: "") }

    val titulo = if (idEquipamento == 0) "Novo equipamento" else "Editar equipamento"
    val podeSalvar = nome.isNotBlank() && tipo.isNotBlank()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                navigationIcon = {
                    IconButton(onClick = aoVoltar) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
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
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedTextField(
                value = nome,
                onValueChange = { nome = it },
                label = { Text("Nome do equipamento") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = tipo,
                onValueChange = { tipo = it },
                label = { Text("Tipo (roteador, switch, access point...)") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
            OutlinedTextField(
                value = ip,
                onValueChange = { ip = it },
                label = { Text("Endereço IP") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
            OutlinedTextField(
                value = local,
                onValueChange = { local = it },
                label = { Text("Localização") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )
            OutlinedTextField(
                value = obs,
                onValueChange = { obs = it },
                label = { Text("Observações") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth().padding(top = 12.dp)
            )

            Button(
                onClick = {
                    viewModel.salvar(
                        Equipamento(
                            id = idEquipamento,
                            nome = nome.trim(),
                            tipo = tipo.trim(),
                            enderecoIp = ip.trim(),
                            localizacao = local.trim(),
                            observacoes = obs.trim()
                        )
                    )
                    aoVoltar()
                },
                enabled = podeSalvar,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp)
            ) {
                Text("Salvar")
            }
        }
    }
}
