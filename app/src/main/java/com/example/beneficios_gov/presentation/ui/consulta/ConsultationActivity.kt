package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.databinding.ActivityConsultationBinding
import com.google.android.material.textfield.TextInputEditText

class ConsultationActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityConsultationBinding.inflate(layoutInflater)
    }

    private fun exibirMensagem(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
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

            btnCPF.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_cpf, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputCpf)

                AlertDialog.Builder(context)
                    .setTitle("Digite o seu CPF")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem("O seu CPF é: $userInput")
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem("Digite o seu CPF")
                        }

                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                    .show()
            }

            btnNIS.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_nis, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputNis)

                AlertDialog.Builder(context)
                    .setTitle("Digite o seu NIS")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem("o Número do seu NIS é: $userInput")
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem("Digite o seu NIS")
                        }
                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                    .show()
            }

            btnPeriodo.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_periodo, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputPeriodo)

                AlertDialog.Builder(context)
                    .setTitle("Digite o período")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem("O Período consultado é: $userInput")
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem("Digite o periodo que deseja consultar")
                        }
                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                    .show()
            }
            dialogChoice.show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}