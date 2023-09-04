package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.viewmodels.ProductDetailsViewModel


private const val productDetailsRoute = "productDetails"
private const val productIdArgument = "productId"
private const val promoCodeParameter = "promoCode"

fun NavGraphBuilder.productDetailsNavScreen(navController: NavHostController) {
    composable(
        "$productDetailsRoute/{$productIdArgument}?promoCode={$promoCodeParameter}",
        arguments = listOf(navArgument("promoCode") { nullable = true })
    ) { navBackStack ->

        val viewModel = viewModel<ProductDetailsViewModel>()
        val uiState by viewModel.uiState.collectAsState()

        val productId = navBackStack.arguments?.getString(productIdArgument)
        val promoCode = navBackStack.arguments?.getString(promoCodeParameter)

        Log.i("MainActivity", "promoCode: $promoCode")
        Log.i("MainActivity", "productId: $productId")

        if (promoCode == "ALURA") {
            Log.i("MainActivity", "Getting 10% discount")
        }

        productId?.let {
            LaunchedEffect(key1 = Unit) {
                viewModel.findProductById(productId)
            }

            ProductDetailsScreen(
                uiState = uiState,
                onNavigateToCheckout = {
                    navController.navigateToCheckout()
                },
                onRetrySearch = {
                    viewModel.findProductById(productId)
                },
                onBackStack = {
                    navController.popBackStack()
                }
            )
        } ?: LaunchedEffect(Unit) {
            navController.navigateUp()
        }
    }
}

fun NavController.navigateToProductDetails(id: String) {
    navigate("$productDetailsRoute/$id")
}

fun NavController.navigateToProductDetails(id: String, promoCode2: String) {
    navigate("$productDetailsRoute/$id?promoCode=$promoCode2")
}