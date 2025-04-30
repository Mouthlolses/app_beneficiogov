package com.example.beneficios_gov.presentation.ui.consulta

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.dao.ConsultaNisDao
import com.example.beneficios_gov.database.AppDatabase
import com.example.beneficios_gov.databinding.ActivityHistoricoBinding
import com.example.beneficios_gov.presentation.ui.CHAVE_CONSULTA_ID
import com.example.beneficios_gov.presentation.ui.recyclerview.adapter.ListaConsultaAdapter

class HistoricoActivity : AppCompatActivity() {

    private val adapter = ListaConsultaAdapter(context = this)

    private val consultaNisDao: ConsultaNisDao by lazy {
        val db = AppDatabase.instancia(this)
        db.consultaNisItem()
    }
    private val binding by lazy {
        ActivityHistoricoBinding.inflate(layoutInflater)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        inicializarToolBar()
        configuraRecyclerView()
        val consultas = consultaNisDao.buscatodos()
        adapter.atualiza(consultas)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instancia(this)
        val consultasDao = db.consultaNisItem()
        adapter.atualiza(consultasDao.buscatodos())
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerviewconsultas
        recyclerView.adapter = adapter

        adapter.quandoClicarNoItem = {
            val intent = Intent(
                this,
                DetalhesConsultaActivity::class.java
            ).apply {
                putExtra(CHAVE_CONSULTA_ID, it.id1)
            }
            startActivity(intent)
        }

    }

    private fun inicializarToolBar() {
        binding.includedToolBarHistoryActivity.constraintLogo.visibility = View.GONE
        binding.includedToolBarHistoryActivity.materialToolbar.title = "Histórico de consultas"
        setSupportActionBar(binding.includedToolBarHistoryActivity.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}