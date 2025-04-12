package com.example.beneficios_gov.presentation.ui.consulta

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.beneficios_gov.R
import com.example.beneficios_gov.database.DatabaseHelper
import com.example.beneficios_gov.databinding.ActivityConsultationBinding
import com.google.android.material.textfield.TextInputEditText

class ConsultationActivity : AppCompatActivity() {


    private val binding by lazy {
        ActivityConsultationBinding.inflate(layoutInflater)
    }

    private val database by lazy {
        DatabaseHelper(this)
    }

    private fun exibirMensagem(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.cardView.setOnClickListener {
            val context = it.context
            val dialogViewChoice =
                LayoutInflater.from(context).inflate(R.layout.dialog_choice, null)

            val btnCPF = dialogViewChoice.findViewById<Button>(R.id.btnCPF)
            val btnNIS = dialogViewChoice.findViewById<Button>(R.id.btnNIS)
            val btnPeriodo = dialogViewChoice.findViewById<Button>(R.id.btnPeriodo)

            val dialogChoice = AlertDialog.Builder(context)
                .setTitle("Como deseja consultar?")
                .setView(dialogViewChoice)
                .setNegativeButton("Fechar", null)
                .create()
            dialogChoice.setOnShowListener {
                dialogChoice.getButton(AlertDialog.BUTTON_NEGATIVE)
                    ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
            }

            btnCPF.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_cpf, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputCpf)

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o seu CPF")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem("O seu CPF é: $userInput")
                            salvar(userInput)
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem("Digite o seu CPF")
                        }
                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                alertDialog.setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.verde_esmeralda))

                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                alertDialog.show()
            }

            btnNIS.setOnClickListener {

                val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_nis, null)
                val editText = dialogView.findViewById<TextInputEditText>(R.id.editTextInputNis)


                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o seu NIS")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem("o Número do seu NIS é: $userInput")
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem("Digite o seu NIS")
                        }
                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                alertDialog.setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.verde_esmeralda))

                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                alertDialog.show()
            }

            btnPeriodo.setOnClickListener {

                val dialogView =
                    LayoutInflater.from(context).inflate(R.layout.dialog_periodo, null)
                val editText =
                    dialogView.findViewById<TextInputEditText>(R.id.editTextInputPeriodo)

                val alertDialog = AlertDialog.Builder(context)
                    .setTitle("Digite o período")
                    .setView(dialogView)
                    .setPositiveButton("CONSULTAR") { _, _ ->
                        val userInput = editText.text?.toString()?.trim() ?: ""
                        if (userInput.isNotEmpty()) {
                            exibirMensagem("O Período consultado é: $userInput")
                            dialogChoice.dismiss()
                        } else {
                            exibirMensagem("Digite o periodo que deseja consultar")
                        }
                    }
                    .setNegativeButton("Fechar", null)
                    .create()
                alertDialog.setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.verde_esmeralda))

                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                        ?.setTextColor(ContextCompat.getColor(context, R.color.gray))
                }
                alertDialog.show()
            }
            dialogChoice.show()
        }

        binding.btnHistorico.setOnClickListener {
            listarConsultaCpf()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun salvar(titleCpf: String) {
        val sql = "INSERT INTO consultas VALUES(null, '$titleCpf', 'Descricao..');"

        try {
            database.writableDatabase.execSQL(sql)
            Log.i("info.db", "Sucesso ao inserir")
        } catch (e: Exception) {
            Log.i("info.db", "Erro ao Inserir, $e")
        }
    }

   /* private fun excluir() {
        val sql =
            "DELETE FROM ${DatabaseHelper.TABELA_CONSULTAS}" +
                    " WHERE ${DatabaseHelper.ID_CONSULTA} = 5"
        try {
            database.writableDatabase.execSQL(sql)
            Log.i("info.db", "Sucesso ao Excluir")
        } catch (e: Exception) {
            Log.i("info.db", "Erro ao Excluir, $e")
        }
    }*/
    /*    private fun atualizar(titleCpf: String){
            val sql = "UPDATE consultas SET titulo = '$titleCpf' WHERE ID_CONSULTA = 1;"

            try {
                database.writableDatabase.execSQL(sql)
                Log.i("info.db", "Sucesso ao Atualizar")
            } catch (e: Exception) {
                Log.i("info.db", "Erro ao Atualizar, $e")
            }
        }*/

    private fun listarConsultaCpf() {
        val sql = "SELECT * FROM ${DatabaseHelper.TABELA_CONSULTAS};"
        val cursor = database.readableDatabase
            .rawQuery(sql, null)

        val indiceConsulta = cursor.getColumnIndex(DatabaseHelper.ID_CONSULTA)
        val indiceTitulo = cursor.getColumnIndex(DatabaseHelper.TITULO)
        val indiceDescricao = cursor.getColumnIndex(DatabaseHelper.DESCRICAO)

        while (cursor.moveToNext()) {
            val idConsulta = cursor.getInt(indiceConsulta)
            val titulo = cursor.getString(indiceTitulo)
            val descricao = cursor.getString(indiceDescricao)
            Log.i("info.db", "id: $idConsulta - $titulo - $descricao")
        }
    }


}