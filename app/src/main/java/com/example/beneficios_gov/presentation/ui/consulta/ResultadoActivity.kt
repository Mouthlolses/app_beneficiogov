package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.dao.ConsultaNisDao
import com.example.beneficios_gov.database.AppDatabase
import com.example.beneficios_gov.databinding.ActivityResultadoBinding

class ResultadoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResultadoBinding.inflate(layoutInflater)
    }

    private val consultaNisDao : ConsultaNisDao by lazy {
        val db = AppDatabase.instancia(this)
        db.consultaNisItem()
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

        binding.meuCard.jogoItemNomeDoOrganizador.text = "Benefíciario: $nome"
        binding.meuCard.jogoItemNumeroParaContato.text = "Municipio: $municipio"
        binding.meuCard.jogoItemDiaDoJogo.text = "Data Referência: $data"
        binding.meuCard.jogoItemValorParaPagar.text = "Valor Sacado: $valor"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun iniciarToolBar() {
        binding.includedToolBarResultActivity.constraintLogo.visibility = View.GONE
        binding.includedToolBarResultActivity.materialToolbar.title = "Consulta"
        setSupportActionBar(binding.includedToolBarResultActivity.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}