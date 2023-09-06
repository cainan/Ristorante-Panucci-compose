package br.com.alura.panucci.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost

internal const val uri: String = "alura://panucci.com.br"

@Composable
fun PanucciNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = homeRoute,
    ) {
        homeGraph(
            onNavigateToCheckout = {
                navController.navigateToCheckout()
            },
            onNavigateToProductDetails = { product ->
                navController.navigateToProductDetails(product.id)
            },
        )
        productDetailsNavScreen(
            onNavigateToCheckout = {
                navController.navigateToCheckout()
            },
            onPopBackStack = {
                navController.navigateUp()
            })
        checkoutNavScreen(onPopBackStack = {
            navController.currentBackStackEntry?.savedStateHandle?.set(
                "order_done",
                "Pedido feito com Sucesso"
            )
            navController.navigateUp()
        })
    }
}

