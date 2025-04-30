package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.repository.ConsultaNisItem
import com.example.beneficios_gov.database.AppDatabase
import com.example.beneficios_gov.databinding.ActivityDetalhesConsultaBinding
import com.example.beneficios_gov.presentation.ui.CHAVE_CONSULTA_ID

class DetalhesConsultaActivity : AppCompatActivity() {

    private var consultaId: Int = 0
    private var consulta: ConsultaNisItem? = null

    private val binding by lazy {
        ActivityDetalhesConsultaBinding.inflate(layoutInflater)
    }

    private val ConsultaDao by lazy {
        AppDatabase.instancia(this).consultaNisItem()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        inicializarToolBar()
        tentaCarregarConsulta()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        buscaConsulta()
    }

    private fun buscaConsulta() {
        consulta = ConsultaDao.buscaPorId(consultaId)
        consulta?.let {
            preencheCampos(it)
        } ?: finish()
    }

    private fun tentaCarregarConsulta() {
        consultaId = intent.getIntExtra(CHAVE_CONSULTA_ID, 0)
    }

    private fun preencheCampos(consultaCarregada: ConsultaNisItem) {
        with(binding) {
            nomeBeneficiarioDetalhes.text = consultaCarregada.beneficiarioNovoBolsaFamilia.nome
            dataDetalhes.text = consultaCarregada.dataMesReferencia
            municipioDetalhes.text = consultaCarregada.municipio.nomeRegiao
            valor.text = consultaCarregada.valorSaque.toString()
        }
    }

    private fun inicializarToolBar() {
        binding.includedToolBarDetalhesActivity.constraintLogo.visibility = View.GONE
        binding.includedToolBarDetalhesActivity.materialToolbar.title = "Detalhes da consulta"
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
                consulta?.let { ConsultaDao.remove(it) }
                finish()
                true
            }
            else -> {
                return super.onOptionsItemSelected(item)
            }
        }
    }
}
