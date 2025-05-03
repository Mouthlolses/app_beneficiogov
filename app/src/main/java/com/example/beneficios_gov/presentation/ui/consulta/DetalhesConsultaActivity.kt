package com.example.beneficios_gov.presentation.ui.consulta

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.repository.ConsultaNisItem
import com.example.beneficios_gov.database.AppDatabase
import com.example.beneficios_gov.databinding.ActivityDetalhesConsultaBinding
import com.example.beneficios_gov.presentation.ui.CHAVE_CONSULTA_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalhesConsultaActivity : AppCompatActivity() {

    private var consultaId: Int = 0
    private var consulta: ConsultaNisItem? = null

    private val binding by lazy {
        ActivityDetalhesConsultaBinding.inflate(layoutInflater)
    }

    private val ConsultaDao by lazy {
        AppDatabase.instance(this).consultaNisItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        bootingToolBar()
        tryLoadConsultation()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
           withContext(Dispatchers.IO) {
                searchConsultation()
            }
        }
    }

    private suspend fun searchConsultation() {
        consulta = ConsultaDao.searchForId(consultaId)
        consulta?.let {
            fillFields(it)
        } ?: finish()
    }

    private fun tryLoadConsultation() {
        consultaId = intent.getIntExtra(CHAVE_CONSULTA_ID, 0)
    }

    private fun fillFields(consultationLoaded: ConsultaNisItem) {
        with(binding) {
            nomeBeneficiarioDetalhes.text = consultationLoaded.beneficiarioNovoBolsaFamilia.nome
            dataDetalhes.text = "Data Referência: ${consultationLoaded.dataMesReferencia}"
            municipioDetalhes.text = consultationLoaded.municipio.nomeRegiao
            valor.text = "Valor do Saque: ${consultationLoaded.valorSaque}"
        }
    }

    private fun bootingToolBar() {
        binding.includedToolBarDetalhesActivity.constraintLogo.visibility = View.GONE
        binding.includedToolBarDetalhesActivity.materialToolbar.title = "Detalhes da consulta"
        binding.includedToolBarDetalhesActivity.materialToolbar.setBackgroundColor(Color.TRANSPARENT)
        setSupportActionBar(binding.includedToolBarDetalhesActivity.materialToolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.excluir -> {
                consulta?.let {
                    lifecycleScope.launch {
                        withContext(Dispatchers.IO) {
                            ConsultaDao.delete(it)
                        }
                        finish()
                    }
                }
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
