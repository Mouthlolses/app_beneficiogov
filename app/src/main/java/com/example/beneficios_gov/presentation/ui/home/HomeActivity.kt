package com.example.beneficios_gov.presentation.ui.home

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.database.DatabaseHelper
import com.example.beneficios_gov.databinding.ActivityHomeBinding
import com.example.beneficios_gov.extensions.vaiPara
import com.example.beneficios_gov.presentation.ui.consulta.ConsultationActivity

class HomeActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    private val database by lazy {
        DatabaseHelper(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        try {
            database.writableDatabase.execSQL(
                "INSERT INTO consultas VALUES(null, 'Consulta 1', 'Descricao..');"
            )
            Log.i("info.db", "Sucesso ao inserir")
        } catch (e: Exception){
            Log.i("info.db", "Erro ao Inserir")
        }


        binding.buttonElevated.setOnClickListener {
            vaiPara(ConsultationActivity::class.java)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}