package com.example.beneficios_gov

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beneficios_gov.presentation.ui.consulta.ConsultationActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BtnCpfTest {

    @Test
    fun aoClicarNoBotaoCpf_abreDialogComTextoEsperado() {
        // Abre a MainActivity (ou a que contém o botão)
        ActivityScenario.launch(ConsultationActivity::class.java)

        onView(withId(R.id.cardView)).perform(click())

        // Clica no botão com o ID btnCPF
        onView(withId(R.id.btnCPF)).perform(click())


        onView(withId(R.id.editTextInputCpf))
            .perform(click())
            .perform(replaceText("12345678911"), closeSoftKeyboard())


        onView(withText("CONSULTAR"))
            .inRoot(isDialog())
            .perform(click())

    }


}