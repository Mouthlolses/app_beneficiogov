package com.example.beneficios_gov.presentation.ui.consulta

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColorInt
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.api.coinApi
import com.example.beneficios_gov.data.api.nisApi
import com.example.beneficios_gov.databinding.ActivityConsultationBinding
import com.example.beneficios_gov.extensions.goTo
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

        window.navigationBarColor = "#061B15".toColorInt()
        configureNotification(this, this)

        binding.cardView.setOnClickListener {
            val context = it.context

            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_nis, null)
            val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputCpf)
            val editText2 = dialogView.findViewById<TextInputEditText>(R.id.editTextInputData)
            val errorEditText = dialogView.findViewById<TextView>(R.id.errorTextView)


            val alertDialog = AlertDialog.Builder(context)
                .setTitle(
                    "> Digite o seu NIS\n" +
                            "> Digite a Data Referência\n"
                )
                .setView(dialogView)
                .setPositiveButton("CONSULTAR", null)
                .setNegativeButton("Fechar", null)
                .create()

            alertDialog.setOnShowListener {
                val positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                positiveButton?.setTextColor(
                    ContextCompat.getColor(
                        context,
                        R.color.verde_esmeralda
                    )
                )
                negativeButton?.setTextColor(ContextCompat.getColor(context, R.color.gray))
                positiveButton.setOnClickListener {
                    val userInput = editText.text?.toString()?.trim() ?: ""
                    val userInputData = editText2.text?.toString()?.trim() ?: ""

                    if (userInput.isEmpty() || userInputData.isEmpty()) {
                        errorEditText.text = "Por favor, preencha todos os campos."
                        errorEditText.visibility = View.VISIBLE
                    } else if (userInput.length != 11 || userInputData.length != 6) {
                        errorEditText.text = "NIS deve ter 11 dígitos e Data 6 (MMYYYY)."
                        errorEditText.visibility = View.VISIBLE
                    } else {
                        errorEditText.visibility = View.GONE
                        alertDialog.dismiss()
                        CoroutineScope(Dispatchers.IO).launch {
                            try {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.visibility = View.VISIBLE
                                    dialogView.visibility = View.GONE
                                }
                                searchNis(userInput, userInputData)
                            } catch (e: Exception) {
                                Log.e("consulta", "Erro: ${e.message}")
                            } finally {
                                withContext(Dispatchers.Main) {
                                    binding.progressBar.visibility = View.GONE
                                }
                            }
                        }
                    }
                }
            }
            alertDialog.show()
        }
        binding.cardView2.setOnClickListener {
            val context = it.context

            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_coin, null)
            val dialogBuilder =  AlertDialog.Builder(context).setView(dialogView)

            val alertDialog = dialogBuilder.create()

            val spinner = dialogView.findViewById<Spinner>(R.id.spinnerCoin)
            val dataPicker = dialogView.findViewById<DatePicker>(R.id.datePicker)
            val btnConfirm = dialogView.findViewById<Button>(R.id.btnConfirm)
            val btnCancel = dialogView.findViewById<Button>(R.id.btnCancel)

            val currencies = listOf("USD","EUR")
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, currencies)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter

            btnCancel.setOnClickListener {
                alertDialog.dismiss()
            }

            btnConfirm.setOnClickListener {
                val selectedCurrency = spinner.selectedItem.toString()
                val day = dataPicker.dayOfMonth
                val month = dataPicker.month
                val year = dataPicker.year

                val selectedDate = "$year-$month-$day"

                    try {
                        lifecycleScope.launch {
                            withContext(Dispatchers.IO) {
                                searchCoin(selectedCurrency, selectedDate)
                                Log.d("searchCoin","Consulta bem sucedida")
                            }
                        }
                    }catch (e: Exception) {
                        Log.d("searchCoin","Ocorreu o erro: ${e.message}")
                    }
                Toast.makeText(context, "Moeda: $selectedCurrency\nData: $selectedDate", Toast.LENGTH_LONG).show()
                alertDialog.dismiss()
            }
            alertDialog.show()

        }

        binding.btnHistorico.setOnClickListener {
            goTo(HistoricoActivity::class.java)
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
                exibirMensagem(this, "Notificações Ativadas")
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
                    val intent =
                        Intent(this@ConsultationActivity, ResultadoActivity::class.java)

                    // Exemplo de como passar dados para a nova activity (pode ser um nome ou uma lista)
                    val id = body?.firstOrNull()?.idAPi
                    val nome =
                        body?.firstOrNull()?.beneficiarioNovoBolsaFamilia?.nome
                            ?: "Nenhum dado encontrado para o NIS informado"
                    val cpfFormatado =
                        body?.firstOrNull()?.beneficiarioNovoBolsaFamilia?.cpfFormatado
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


                    startActivity(intent)
                }
            } else {
                Log.e("info_consulta", "Erro ao obter resposta da API")
            }
        } catch (e: Exception) {
            Log.i("info_consulta", "Consulta não ocorreu: ${e.message}")
        }
    }

    private suspend fun searchCoin(coin: String, dataCoin: String) {
        try {
            val coinApiItem = coinApi
            val response = coinApiItem.searchCotacao(
                moeda = coin,
                data = dataCoin
            )

            if(response.isSuccessful){
                val body = response.body()
                val resultText = body?.cotacoes
                    ?.joinToString(separator = "\n"){
                        "Venda: ${it.cotacaoCompra} | Tipo: ${it.tipoBoletim} | Hora: ${it.dataHoraCotacao}"
                    }
                Log.d("response","${body?.cotacoes?.size}")
                Log.d("response", resultText ?: "Nenhuma cotação encontrada")
            } else {
                Log.d("response","Falhou")
            }
            withContext(Dispatchers.Main) {

            }


        } catch (e: Exception) {
            Log.i("info_consulta", "Consulta não ocorreu: ${e.message}")
        }
    }

}
