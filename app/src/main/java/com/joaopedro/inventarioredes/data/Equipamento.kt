package com.joaopedro.inventarioredes.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * CAMADA DE DADOS (Model)
 * Cada instância desta classe vira uma linha na tabela "equipamentos" do banco.
 */
@Entity(tableName = "equipamentos")
data class Equipamento(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nome: String,
    val tipo: String,
    val enderecoIp: String,
    val localizacao: String,
    val observacoes: String = ""
)
