package com.joaopedro.inventarioredes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Banco de dados Room com 1 entidade (Equipamento).
 * Usamos o padrão Singleton para ter apenas UMA instância do banco no app inteiro.
 */
@Database(entities = [Equipamento::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun equipamentoDao(): EquipamentoDao

    companion object {
        @Volatile
        private var INSTANCIA: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCIA ?: synchronized(this) {
                val db = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "inventario_redes.db"
                ).build()
                INSTANCIA = db
                db
            }
        }
    }
}
