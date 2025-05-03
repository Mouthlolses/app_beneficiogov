package com.example.beneficios_gov.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beneficios_gov.data.repository.ConsultaNisItem

@Dao
interface ConsultaNisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun salve(vararg consulta: ConsultaNisItem)

    @Query("SELECT * FROM ConsultaNisItem")
    suspend fun searchAll(): List<ConsultaNisItem>

    @Delete()
    suspend fun delete(vararg consulta: ConsultaNisItem)

    @Query("SELECT * FROM ConsultaNisItem WHERE id1 = :id")
    suspend fun searchForId(id: Int?) : ConsultaNisItem?
}