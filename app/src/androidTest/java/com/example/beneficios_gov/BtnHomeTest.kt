package com.example.beneficios_gov

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beneficios_gov.presentation.ui.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BtnHomeTest {

    @Test
    fun aoClicarNoBotaoDaHome_levaParaTelaConsulta() {

        ActivityScenario.launch(HomeActivity::class.java)

        onView(withId(R.id.buttonElevated))
            .perform(click())

    }
}