package com.example.beneficios_gov

import com.example.beneficios_gov.data.api.moedaApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Assert.fail
import org.junit.Test
import kotlin.test.assertTrue

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ApiUnitTest {


    @Test
    fun buscarDadosNaApi() = runBlocking {
        val response = moedaApi.searchCotacao("USD","2025-02-13")

        if(response.isSuccessful) {
            val body = response.body()
            assertNotNull(body)
            assertTrue(body!!.cotacoes.isNotEmpty())

            println("Total de cotacoes: ${body.cotacoes.size}")
            body.cotacoes.forEach {
                println("Venda: ${it.cotacaoVenda} | Tipo: ${it.tipoBoletim} | Hora: ${it.dataHoraCotacao}")
            }

        } else {
            val error = response.errorBody()?.string()
            fail("Erro da API: ${response.code()} - $error")

        }
    }
}