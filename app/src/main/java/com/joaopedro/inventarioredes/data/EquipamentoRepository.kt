package com.joaopedro.inventarioredes.data

import kotlinx.coroutines.flow.Flow

/**
 * O Repositório isola o resto do app dos detalhes de onde os dados vêm.
 * Hoje é Room; amanhã poderia ser uma API, sem mudar o ViewModel.
 */
class EquipamentoRepository(private val dao: EquipamentoDao) {

    fun listarTodos(): Flow<List<Equipamento>> = dao.listarTodos()

    suspend fun inserir(equipamento: Equipamento) = dao.inserir(equipamento)

    suspend fun atualizar(equipamento: Equipamento) = dao.atualizar(equipamento)

    suspend fun deletar(equipamento: Equipamento) = dao.deletar(equipamento)
}
