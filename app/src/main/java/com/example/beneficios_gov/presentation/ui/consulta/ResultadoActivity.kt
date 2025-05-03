package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.dao.ConsultaNisDao
import com.example.beneficios_gov.data.repository.BeneficiarioNovoBolsaFamilia
import com.example.beneficios_gov.data.repository.ConsultaNisItem
import com.example.beneficios_gov.data.repository.Municipio
import com.example.beneficios_gov.data.repository.Uf
import com.example.beneficios_gov.database.AppDatabase
import com.example.beneficios_gov.databinding.ActivityResultadoBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ResultadoActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityResultadoBinding.inflate(layoutInflater)
    }

    private var consultaId = 0

    private val consultaNisDao: ConsultaNisDao by lazy {
        val db = AppDatabase.instance(this)
        db.consultaNisItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        iniciarToolBar()
        configuraBotaoSalvar()
        // Recuperar os dados passados pela Intent
        val nome = intent.getStringExtra("nome") ?: "Nenhum dado encontrado para o NIS informado"
        val municipio = intent.getStringExtra("municipio") ?: ""
        val data = intent.getStringExtra("data") ?: ""
        val valor = intent.getIntExtra("valor", 0)

        binding.meuCard.nomeBeneficiario.text = "Benefíciario: $nome"
        binding.meuCard.municipio.text = "Região: $municipio"
        binding.meuCard.dataConsulta.text = "Data Referência: $data"
        binding.meuCard.valorSacado.text = "Valor Sacado: $valor"

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun configuraBotaoSalvar() {
        val nome = criaConsulta().beneficiarioNovoBolsaFamilia.nome

        if (nome == "Benefíciario: Nenhum dado encontrado para o NIS informado") {
            binding.btnSalvar.visibility = View.GONE
        } else {
            binding.btnSalvar.visibility = View.VISIBLE
            iniciarbBotaoSalvar()
        }
    }

    private fun iniciarbBotaoSalvar() {
        val consulta = criaConsulta()
        binding.btnSalvar.setOnClickListener {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    consultaNisDao.salve(consulta)
                }
                finish()
            }
        }
    }

    private fun criaConsulta(): ConsultaNisItem {
        val id = intent.getIntExtra("id", 0)
        val nome = intent.getStringExtra("nome") ?: "Nenhum dado encontrado para o NIS informado"
        val municipio = intent.getStringExtra("municipio") ?: ""
        val data = intent.getStringExtra("data") ?: ""
        val valor = intent.getIntExtra("valor", 0)

        val uf = Uf("", "")
        val beneficiario = BeneficiarioNovoBolsaFamilia("", "", "Benefíciario: $nome")
        val municipioReal = Municipio("", "", "", "Região: $municipio", "", uf)

        return ConsultaNisItem(
            id1 = consultaId,
            beneficiarioNovoBolsaFamilia = beneficiario,
            dataMesCompetencia = "",
            dataMesReferencia = data,
            idAPi = id,
            municipio = municipioReal,
            valorSaque = valor
        )

    }


    private fun iniciarToolBar() {
        binding.includedToolBarResultActivity.constraintLogo.visibility = View.GONE
        binding.includedToolBarResultActivity.materialToolbar.title = "Consulta"
        setSupportActionBar(binding.includedToolBarResultActivity.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}