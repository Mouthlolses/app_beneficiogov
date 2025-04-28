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
        iniciarToolBar()
        // Recuperar os dados passados pela Intent
        val nome = intent.getStringExtra("nome") ?: "Nenhum dado encontrado para o NIS informado"
        val municipio = intent.getStringExtra("municipio") ?: ""
        val data = intent.getStringExtra("data") ?: ""
        val valor = intent.getStringExtra("valor") ?: ""

        binding.textViewNomeResultado.text = "Benefíciario: $nome"
        binding.textViewMunicipio.text = "Municipio: $municipio"
        binding.textViewDataMesReferencia.text = "Data Referência: $data"
        binding.textViewValor.text = "Valor Sacado: $valor"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun iniciarToolBar() {
        binding.includedToolBarResultActivity.materialToolbar.title = "Consulta"
        setSupportActionBar(binding.includedToolBarResultActivity.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}