package com.example.beneficios_gov.presentation.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.beneficios_gov.data.repository.ConsultaNisItem
import com.example.beneficios_gov.databinding.CardConsultaBinding

class ListaConsultaAdapter(
    private val context: Context,
    consultations: List<ConsultaNisItem> = emptyList(),

    var whenClickItem: (myConsultation: ConsultaNisItem) -> Unit = {}
) : RecyclerView.Adapter<ListaConsultaAdapter.ViewHolder>() {

    private val consultation = consultations.toMutableList()

    inner class ViewHolder(private val binding: CardConsultaBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var consultas: ConsultaNisItem

        init {
            // implementação do listener do adapter
            itemView.setOnClickListener {
                // verificação da existência de valores em property lateinit
                if (::consultas.isInitialized) {
                    whenClickItem(consultas)
                }
            }
        }

        fun bind(consultation: ConsultaNisItem) {
            this.consultas = consultation
            val nomeConsultado = binding.nomeBeneficiario
            nomeConsultado.text = consultas.beneficiarioNovoBolsaFamilia.nome
            val cpfFormatado = binding.cpfConsultado
            cpfFormatado.text = consultas.beneficiarioNovoBolsaFamilia.cpfFormatado
            val data = binding.dataConsulta
            data.text = "Data Referência: ${consultas.dataMesReferencia}"
            val municipio = binding.municipio
            municipio.text = consultas.municipio.nomeRegiao
            val ufSigla = binding.ufSigla
            ufSigla.text = consultas.municipio.uf.sigla
            val nomeIBGE = binding.nomeIBGE
            nomeIBGE.text = consultas.municipio.nomeIBGE
            val valor = binding.valorSacado
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
        val item = consultation[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = consultation.size

    fun update(newConsultation: List<ConsultaNisItem>) {
        consultation.clear()
        consultation.addAll(newConsultation)
        notifyDataSetChanged()
    }
}