package com.example.beneficios_gov.presentation.ui.consulta

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.api.nisApi
import com.example.beneficios_gov.database.ConsultaDAO
import com.example.beneficios_gov.databinding.ActivityConsultationBinding
import com.example.beneficios_gov.model.Consulta
import com.example.beneficios_gov.utils.exibirMensagem
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConsultationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityConsultationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        toolBarConsult()

        binding.cardView.setOnClickListener {
            val context = it.context
            val dialogViewChoice =
                LayoutInflater.from(context).inflate(R.layout.dialog_choice, null)

            val btnNIS = dialogViewChoice.findViewById<Button>(R.id.btnNIS)


            val dialogChoice = AlertDialog.Builder(context)
                .setTitle("Realize sua consulta")
                .setView(dialogViewChoice)
                .setNegativeButton("Fechar", null)
                .create()
            dialogChoice.setOnShowListener {
                dialogChoice.getButton(AlertDialog.BUTTON_NEGATIVE)
                    ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }

            btnNIS.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_nis, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputCpf)
                val editText2 = dialogView.findViewById<TextInputEditText>(R.id.editTextInputData)

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o seu NIS e a Data a ser consultada.")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        val userInputData = editText2.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty() && userInputData.isNotEmpty()) {
                            exibirMensagem(
                                this,
                                "O seu NIS é: $userInput, Data pesquisada: $userInputData"
                            )

                            salvar(userInput, userInputData)

                            CoroutineScope(Dispatchers.IO).launch {
                                try {
                                    Log.d(
                                        "info_consulta",
                                        "Iniciando a consulta NIS com o código: $userInput"
                                    )
                                    pesquisarCpf(userInput, userInputData)
                                    Log.d("info_consulta", "Consulta NIS realizada com sucesso")
                                } catch (e: Exception) {
                                    Log.i("info_consulta", "Erro na consulta ${e.message}")
                                }
                            }

                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem(this, "Digite o seu NIS")
                        }
                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                alertDialog.setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.verde_esmeralda))

                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                alertDialog.show()
            }

            dialogChoice.show()
        }

        /* with(binding) {
             btnHistorico.setOnClickListener {
                 groupMenu.visibility = if (groupMenu.isVisible) {
                     View.INVISIBLE
                 } else {
                     View.VISIBLE
                 }
             }
         }*/

        binding.btnHistorico.setOnClickListener {
            listarConsultaCpf()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun salvar(userInput: String, userInput2: String) {
        val consultaDAO = ConsultaDAO(this)
        val consulta = Consulta(
            -1,
            userInput,
            userInput2
        )
        if (consultaDAO.salvar(consulta)) {
            exibirMensagem(
                this,
                "Sucesso ao Salvar Consulta"
            )
        } else {
            exibirMensagem(
                this,
                "Falha ao Salvar Consulta"
            )
        }
    }

    private fun excluir() {
        val consultaDAO = ConsultaDAO(this)
        consultaDAO.remover(1)
    }

    private fun atualizar() {

        val transform = findViewById<TextInputEditText>(R.id.editTextInputCpf)
        val titulo = transform.text.toString().trim()
        val consultaDAO = ConsultaDAO(this)
        val consulta = Consulta(
            -1,
            titulo,
            "descricao"
        )
        consultaDAO.atualizar(consulta)
    }

    private fun listarConsultaCpf() {

        val consultaDAO = ConsultaDAO(this)
        val listaDeConsulta = consultaDAO.listar()

        var texto = ""
        val intent = Intent(this, HistoricoActivity::class.java)

        if (listaDeConsulta.isNotEmpty()) {
            listaDeConsulta.forEach { consulta ->
                texto += "Id: ${consulta.idConsulta} - Nome: ${consulta.nome} - Data: ${consulta.data} \n"
                Log.i(
                    "info.db",
                    " Id - ${consulta.idConsulta} - Nome: ${consulta.nome} - Data: ${consulta.data}"
                )
            }
            intent.putExtra("historico_consulta", texto)
            startActivity(intent)
        } else {
            val texto2 = "Nada encontrado"
            intent.putExtra("historico_consulta_nao_encontrado", texto2)
            startActivity(intent)
        }
    }

    private fun toolBarConsult() {
        binding.includedToolBar.materialToolbar.inflateMenu(R.menu.menu_principal)
    }

    private suspend fun pesquisarCpf(nis: String, data: String) {

        try {
            val cpfApi = nisApi
            val response = cpfApi.consultarNis(
                nis = nis,
                anoMesReferencia = data,
                pagina = 1
            )
            Log.d("info_consulta", "Código da resposta: ${response.code()}")
            Log.d("info_consulta", "Body da resposta: ${response.body()}")
            Log.d("info_consulta", "Erro: ${response.errorBody()?.string()}")
            if (response.isSuccessful && response.body() != null) {
                val body = response.body()

                if (body?.isEmpty() == true) {
                    Log.d("info_consulta", "Nenhum dado encontrado para o NIS: $nis")
                } else {
                    body?.forEach {
                        Log.d("info_consulta", "Nome: ${it.beneficiarioNovoBolsaFamilia.nome}")
                    }
                }

                withContext(Dispatchers.Main) {
                    // Criando um Intent para a nova Activity
                    val intent = Intent(this@ConsultationActivity, ResultadoActivity::class.java)

                    // Exemplo de como passar dados para a nova activity (pode ser um nome ou uma lista)
                    val nome =
                        body?.firstOrNull()?.beneficiarioNovoBolsaFamilia?.nome
                            ?: "Nenhum dado encontrado para o NIS informado"
                    val municipio = body?.firstOrNull()?.municipio?.nomeRegiao
                    val data = body?.firstOrNull()?.dataMesReferencia
                    val valor = body?.firstOrNull()?.valorSaque?.toString() ?: ""

                    intent.putExtra("nome", nome)
                    intent.putExtra("municipio", municipio)
                    intent.putExtra("data", data)
                    intent.putExtra("valor", valor)

                    // Iniciar a nova Activity
                    startActivity(intent)
                }
            } else {
                Log.e("info_consulta", "Erro ao obter resposta da API")
            }
        } catch (e: Exception) {
            Log.i("info_consulta", "Consulta não ocorreu: ${e.message}")
        }
    }
}
