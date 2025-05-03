package com.example.beneficios_gov.presentation.ui.consulta

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.beneficios_gov.R
import com.example.beneficios_gov.data.dao.ConsultaNisDao
import com.example.beneficios_gov.database.AppDatabase
import com.example.beneficios_gov.databinding.ActivityHistoricoBinding
import com.example.beneficios_gov.presentation.ui.CHAVE_CONSULTA_ID
import com.example.beneficios_gov.presentation.ui.recyclerview.adapter.ListaConsultaAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoricoActivity : AppCompatActivity() {

    private val adapter = ListaConsultaAdapter(context = this)

    private val consultaNisDao: ConsultaNisDao by lazy {
        val db = AppDatabase.instance(this)
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
        lifecycleScope.launch {
            val consultas = withContext(Dispatchers.IO) {
                consultaNisDao.searchAll()
            }
            adapter.update(consultas)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val consultasDao = db.consultaNisItem()
        lifecycleScope.launch {
           val consultas = withContext(Dispatchers.IO) {
                consultasDao.searchAll()
            }
            adapter.update(consultas)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerviewconsultas
        recyclerView.adapter = adapter

        adapter.whenClickItem = {
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