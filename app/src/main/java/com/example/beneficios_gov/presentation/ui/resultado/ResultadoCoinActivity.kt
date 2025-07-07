package com.example.beneficios_gov.presentation.ui.resultado

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.databinding.ActivityResultadoCoinBinding

class ResultadoCoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultadoCoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResultadoCoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val resultText = intent.getStringExtra("result") ?: "Nenhum Resultado"
        val resultCoin = intent.getStringExtra("coinChose") ?: "Nenhum Resultado"

        binding.meuCardCoin.paridadeCompra.text = "$resultText"
        binding.meuCardCoin.moedaConsultada.text = "$resultCoin"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}