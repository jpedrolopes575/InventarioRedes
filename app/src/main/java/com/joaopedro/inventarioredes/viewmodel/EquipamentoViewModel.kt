package com.joaopedro.inventarioredes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.joaopedro.inventarioredes.data.Equipamento
import com.joaopedro.inventarioredes.data.EquipamentoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * CAMADA DE LÓGICA (o "VM" do MVVM).
 * A tela nunca fala com o banco: ela observa este estado e chama estas funções.
 */
class EquipamentoViewModel(
    private val repositorio: EquipamentoRepository
) : ViewModel() {

    val equipamentos: StateFlow<List<Equipamento>> =
        repositorio.listarTodos().stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun buscarPorId(id: Int): Equipamento? =
        equipamentos.value.find { it.id == id }

    fun salvar(equipamento: Equipamento) {
        viewModelScope.launch {
            if (equipamento.id == 0) {
                repositorio.inserir(equipamento)
            } else {
                repositorio.atualizar(equipamento)
            }
        }
    }

    fun excluir(equipamento: Equipamento) {
        viewModelScope.launch {
            repositorio.deletar(equipamento)
        }
    }
}

/**
 * Fábrica: ensina o Android a construir o ViewModel passando o repositório.
 */
class EquipamentoViewModelFactory(
    private val repositorio: EquipamentoRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EquipamentoViewModel::class.java)) {
            return EquipamentoViewModel(repositorio) as T
        }
        throw IllegalArgumentException("ViewModel desconhecido")
    }
}
