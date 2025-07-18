package com.example.beneficios_gov.presentation.ui.consulta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.beneficios_gov.data.api.nisApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConsultationViewModel : ViewModel() {

    private val _nis = MutableStateFlow("")
    private val nis: StateFlow<String> = _nis

    private val _data = MutableStateFlow("")
    private val data: StateFlow<String> = _data

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage


    private fun updatedNis(newNis: String){
        _nis.value = newNis
    }

    private fun updatedData(newData: String){
        _data.value = newData
    }

    private suspend fun searchNis() {
        val nisApiItem = nisApi
        nisApiItem.consultarNis(
            nis = nis.value,
            anoMesReferencia = data.value,
            pagina = 1,
        )

        if (_nis.value.isBlank() || _data.value.isBlank()){
            _errorMessage.value = "Por favor, preencha todos os campos"
            return
        } else if (_nis.value.length != 17 || _data.value.length != 6) {
            _errorMessage.value = "NIS deve ter 11 dígitos e Data 6 (MMYYYY)."
            return
        } else {
            viewModelScope.launch {
                runCatching {

                }.onSuccess {

                }.onFailure {

                }
            }
        }
    }

}