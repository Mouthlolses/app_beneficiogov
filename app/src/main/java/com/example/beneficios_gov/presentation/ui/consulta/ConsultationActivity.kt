package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import android.view.LayoutInflater
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
            val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null)
            val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInput)

            val openDialog = AlertDialog.Builder(context)
                .setTitle("Informe o seu CPF")
                .setView(dialogView)
                .setPositiveButton("OK") { _, _ ->
                    val userInput = editText.text?.toString()?.trim() ?: ""
                    if (userInput.isNotEmpty()) {
                        exibirMensagem("Seu CPF é: $userInput")
                    } else {
                        exibirMensagem("Digite o seu CPF")
                    }
                }
                .setNegativeButton("Fechar", null)
                .create()

            openDialog.show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}