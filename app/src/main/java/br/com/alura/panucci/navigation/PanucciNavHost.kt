package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

@Composable
fun PanucciNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = highlightsRoute,
    ) {
        highlightsNavScreen(navController)
        menuNavScreen(navController)
        drinksNavScreen(navController)
        productDetailsNavScreen(navController)
        checkoutNavScreen(navController)
    }
}







