package com.example.beneficios_gov.presentation.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beneficios_gov.data.repository.ConsultaNisItem
import com.example.beneficios_gov.databinding.CardConsultaBinding

class ListaConsultaAdapter(
    private val context: Context,
    consultas: List<ConsultaNisItem> = emptyList()
) : RecyclerView.Adapter<ListaConsultaAdapter.ViewHolder>() {

    private val consulta = consultas.toMutableList()

    inner class ViewHolder(private val binding: CardConsultaBinding) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var consultas : ConsultaNisItem

        /*init {
            // implementação do listener do adapter
            itemView.setOnClickListener {
                // verificação da existência de valores em property lateinit
                if (::jogos.isInitialized) {
                    quandoClicaNoItem(jogos)
                }
            }
        }*/

        fun vincula(consulta: ConsultaNisItem){
            this.consultas = consulta
            val nomeConsultado = binding.jogoItemNomeDoOrganizador
            nomeConsultado.text = consultas.beneficiarioNovoBolsaFamilia.nome
            val data = binding.jogoItemNumeroParaContato
            data.text = "Data Referência: ${consultas.dataMesReferencia}"
            val municipio = binding.jogoItemDiaDoJogo
            municipio.text  = consultas.municipio.nomeRegiao
            val valor = binding.jogoItemValorParaPagar
            valor.text = "Valor Sacado: ${consultas.valorSaque}"

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = CardConsultaBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val item = consulta[position]
        holder.vincula(item)
    }

    override fun getItemCount(): Int  = consulta.size

    fun atualiza(novasConsultas: List<ConsultaNisItem>){
        consulta.clear()
        consulta.addAll(novasConsultas)
        notifyDataSetChanged()
    }
}