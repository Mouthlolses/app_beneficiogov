package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.databinding.ActivityHistoricoBinding

class HistoricoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHistoricoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        inicializarToolBar()
        val historico = intent.getStringExtra("historico_consulta")
            ?: intent.getStringExtra("historico_consulta_nao_encontrado")
            ?: "Nenhum dado disponível"

        binding.viewHistoricoConsulta.text = historico

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    private fun inicializarToolBar(){
        binding.includedToolBarHistoryActivity.materialToolbar.title = "Histórico de consultas"
        setSupportActionBar(binding.includedToolBarHistoryActivity.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}