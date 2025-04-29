package com.example.beneficios_gov.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.beneficios_gov.data.repository.ConsultaNisItem

@Dao
interface ConsultaNisDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun salva(vararg consulta: ConsultaNisItem)

    @Query("SELECT * FROM ConsultaNisItem")
    fun buscatodos(): List<ConsultaNisItem>
}