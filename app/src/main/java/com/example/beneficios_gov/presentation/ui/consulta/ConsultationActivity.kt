package com.example.beneficios_gov.presentation.ui.consulta

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.api.nisApi
import com.example.beneficios_gov.databinding.ActivityConsultationBinding
import com.example.beneficios_gov.extensions.OnCheckedChangeListener
import com.example.beneficios_gov.extensions.vaiPara
import com.example.beneficios_gov.notification.configureNotification
import com.example.beneficios_gov.presentation.ui.REQUEST_CODE_POST_NOTIFICATIONS
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

        configureNotification(this, this)

        binding.cardView.setOnClickListener {
            val context = it.context

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
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                Log.d(
                                    "info_consulta",
                                    "Iniciando a consulta NIS com o código: $userInput"
                                )
                                searchNis(userInput, userInputData)
                                Log.d("info_consulta", "Consulta NIS realizada com sucesso")
                            } catch (e: Exception) {
                                Log.i("info_consulta", "Erro na consulta ${e.message}")
                            } finally {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                        }
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
        val cardView2 = binding.cardView2
        cardView2.OnCheckedChangeListener(false)
        binding.btnHistorico.setOnClickListener {
            vaiPara(HistoricoActivity::class.java)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_POST_NOTIFICATIONS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                exibirMensagem(this,"Notificações Ativadas")
            } else {
                null
            }
        }
    }


    private suspend fun searchNis(nis: String, data: String) {

        try {
            val nisApiItem = nisApi
            val response = nisApiItem.consultarNis(
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
                    val id = body?.firstOrNull()?.idAPi?.toInt()
                    val nome =
                        body?.firstOrNull()?.beneficiarioNovoBolsaFamilia?.nome
                            ?: "Nenhum dado encontrado para o NIS informado"
                    val cpfFormatado = body?.firstOrNull()?.beneficiarioNovoBolsaFamilia?.cpfFormatado
                    val municipio = body?.firstOrNull()?.municipio?.nomeRegiao
                    val ufSigla = body?.firstOrNull()?.municipio?.uf?.sigla
                    val cidade = body?.firstOrNull()?.municipio?.nomeIBGE
                    val data = body?.firstOrNull()?.dataMesReferencia
                    val valor = body?.firstOrNull()?.valorSaque

                    intent.putExtra("id", id)
                    intent.putExtra("nome", nome)
                    intent.putExtra("cpfFormatado", cpfFormatado)
                    intent.putExtra("municipio", municipio)
                    intent.putExtra("estado", ufSigla)
                    intent.putExtra("cidade", cidade)
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
