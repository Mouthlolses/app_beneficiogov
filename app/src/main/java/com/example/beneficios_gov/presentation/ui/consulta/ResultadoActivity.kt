package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.databinding.ActivityResultadoBinding

class ResultadoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResultadoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        // Recuperar os dados passados pela Intent
        val nome = intent.getStringExtra("nome") ?: "Nenhum dados encontrado para o CPF pesquisado"

        binding.textViewResultado.text = nome

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}