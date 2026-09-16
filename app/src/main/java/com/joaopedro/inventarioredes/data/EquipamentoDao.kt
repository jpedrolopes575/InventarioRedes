package com.joaopedro.inventarioredes.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * DAO = Data Access Object.
 * É aqui que declaramos as operações de banco. O Room gera o código SQL por trás.
 */
@Dao
interface EquipamentoDao {

    @Query("SELECT * FROM equipamentos ORDER BY nome ASC")
    fun listarTodos(): Flow<List<Equipamento>>

    @Insert
    suspend fun inserir(equipamento: Equipamento)

    @Update
    suspend fun atualizar(equipamento: Equipamento)

    @Delete
    suspend fun deletar(equipamento: Equipamento)
}
