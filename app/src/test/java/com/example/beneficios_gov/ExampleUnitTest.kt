package com.example.beneficios_gov

import com.example.beneficios_gov.data.api.nisApi
import kotlinx.coroutines.runBlocking
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    @Test
    fun buscarDadosNaApi() = runBlocking {

        val userInput = "16315869824"
        val userDataInput = "202501"

        val response = nisApi.consultarNis(
            nis  = userInput,
            anoMesReferencia = userDataInput,
            pagina = 1
        )

        print(response)
    }
}