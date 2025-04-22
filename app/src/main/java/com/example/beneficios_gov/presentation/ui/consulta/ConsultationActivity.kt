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
import com.example.beneficios_gov.database.ConsultaDAO
import com.example.beneficios_gov.databinding.ActivityConsultationBinding
import com.example.beneficios_gov.model.Consulta
import com.example.beneficios_gov.utils.exibirMensagem
import com.google.android.material.textfield.TextInputEditText

class ConsultationActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityConsultationBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.cardView.setOnClickListener {
            val context = it.context
            val dialogViewChoice =
                LayoutInflater.from(context).inflate(R.layout.dialog_choice, null)

            val btnCPF = dialogViewChoice.findViewById<Button>(R.id.btnCPF)
            val btnNIS = dialogViewChoice.findViewById<Button>(R.id.btnNIS)
            val btnPeriodo = dialogViewChoice.findViewById<Button>(R.id.btnPeriodo)

            val dialogChoice = AlertDialog.Builder(context)
                .setTitle("Como deseja consultar?")
                .setView(dialogViewChoice)
                .setNegativeButton("Fechar", null)
                .create()
            dialogChoice.setOnShowListener {
                dialogChoice.getButton(AlertDialog.BUTTON_NEGATIVE)
                    ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }

            btnCPF.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_cpf, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputCpf)

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o seu CPF")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem(this, "O seu CPF é: $userInput")
                            salvar(userInput)
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem(this, "Digite o seu CPF")
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

            btnNIS.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_nis, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputNis)


                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o seu NIS")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem(this, "o Número do seu NIS é: $userInput")
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

            btnPeriodo.setOnClickListener {

                val dialogView =
                    LayoutInflater.from(context).inflate(R.layout.dialog_periodo, null)
                val editText =
                    dialogView.findViewById<TextInputEditText>(R.id.editTextInputPeriodo)

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o período")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem(this, "O Período consultado é: $userInput")
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem(this, "Digite o periodo que deseja consultar")
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

        binding.btnHistorico.setOnClickListener {
            listarConsultaCpf()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun salvar(userInput: String) {
        val consultaDAO = ConsultaDAO(this)
        val consulta = Consulta(
            -1,
            userInput,
            "descricao"
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
                texto += "${consulta.idConsulta} - ${consulta.titulo} \n"
                Log.i("info.db", "${consulta.idConsulta} - ${consulta.titulo}")
            }
            intent.putExtra("historico_consulta", texto)
            startActivity(intent)
        } else {
            val texto2 = "Nada encontrado"
            intent.putExtra("historico_consulta_nao_encontrado", texto2)
            startActivity(intent)
        }
    }

}