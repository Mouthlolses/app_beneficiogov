package com.example.beneficios_gov.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.beneficios_gov.data.dao.ConsultaNisDao
import com.example.beneficios_gov.data.repository.ConsultaNisItem

@Database(entities = [ConsultaNisItem::class], version = 1, exportSchema = false)
@TypeConverters()
abstract class AppDatabase : RoomDatabase() {
    abstract fun consultaNisItem(): ConsultaNisDao
    companion object {
        fun instancia(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "cadFacil.db"
            ).allowMainThreadQueries()
                .build()
        }
    }
}