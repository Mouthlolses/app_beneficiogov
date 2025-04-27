package com.example.beneficios_gov.utils

import android.content.Context
import android.widget.Toast


fun exibirMensagem(context: Context, text: String) {
    Toast.makeText(context, text, Toast.LENGTH_LONG).show()
}