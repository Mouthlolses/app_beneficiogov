package com.example.beneficios_gov.extensions

import com.google.android.material.card.MaterialCardView


fun MaterialCardView.OnCheckedChangeListener(enable: Boolean){
    this.isEnabled = enable
    this.alpha = if(enable) 1f else 0.5f
}