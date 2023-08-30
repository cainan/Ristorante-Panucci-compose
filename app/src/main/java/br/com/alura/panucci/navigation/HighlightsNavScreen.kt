package br.com.alura.panucci.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.HighlightsListScreen

internal const val highlightsRoute = "highlights"
fun NavGraphBuilder.highlightsNavScreen(navController: NavHostController) {
    composable(highlightsRoute) {
        HighlightsListScreen(
            products = sampleProducts,
            onNavigateToDetails = { product ->
                val discount = "ALURA"
                navController.navigateToProductDetails(product.id, discount)
            },
            onNavigateToCheckout = {
                navController.navigateToCheckout()
            }
        )
    }
}

fun NavController.navigateToHighlights(navOptions: NavOptions? = null) {
    navigate(highlightsRoute, navOptions)
}