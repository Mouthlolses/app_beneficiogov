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
import com.example.beneficios_gov.databinding.ActivityHistoricoBinding
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

    private fun configuraRecyclerView(){
        val recyclerView = binding.recyclerviewconsultas
        recyclerView.adapter = adapter
    }

    private fun inicializarToolBar() {
        binding.includedToolBarHistoryActivity.constraintLogo.visibility = View.GONE
        binding.includedToolBarHistoryActivity.materialToolbar.title = "Histórico de consultas"
        setSupportActionBar(binding.includedToolBarHistoryActivity.materialToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}