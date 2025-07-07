package com.example.beneficios_gov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beneficios_gov.data.dao.ConsultaNisDao
import com.example.beneficios_gov.data.model.nis.ConsultaNisItem

@Database(entities = [ConsultaNisItem::class], version = 1, exportSchema = false)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun consultaNisItem(): ConsultaNisDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun instance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cadFacil.db"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}