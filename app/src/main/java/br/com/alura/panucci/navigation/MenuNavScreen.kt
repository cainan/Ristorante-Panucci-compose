package br.com.alura.panucci.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.MenuListScreen

fun NavGraphBuilder.menuNavScreen(navController: NavHostController) {
    composable(AppDestinations.Menu.route) {
        MenuListScreen(
            products = sampleProducts,
            onNavigateToDetails = { product ->
                navController.navigate("${AppDestinations.ProductDetails.route}/${product.id}")
            })
    }
}