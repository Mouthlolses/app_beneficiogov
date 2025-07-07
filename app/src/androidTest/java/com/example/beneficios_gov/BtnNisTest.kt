package com.example.beneficios_gov

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beneficios_gov.presentation.ui.consulta.ConsultationActivity
import com.example.beneficios_gov.presentation.ui.resultado.ResultadoActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BtnNisTest {

    @Test
    fun aoClicarNoCard_abreDialogComTextoEsperado() {
        // Abre a Activity que contém o botão
        ActivityScenario.launch(ConsultationActivity::class.java)

        onView(withId(R.id.cardView)).perform(click())

        onView(withId(R.id.editTextInputCpf))
            .perform(click())
            .perform(replaceText("16315869824"))

        onView(withId(R.id.editTextInputData))
            .perform(click())
            .perform(replaceText("202501"))

        onView(withText("CONSULTAR"))
            .inRoot(isDialog())
            .perform(click())

        ActivityScenario.launch(ResultadoActivity::class.java)

        onView(withId(R.id.btnSalvar))
            .perform(click())


    }


}