package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.viewmodels.ProductDetailsViewModel


private const val productDetailsRoute = "productDetails"
internal const val productIdArgument = "productId"
private const val promoCodeParameter = "promoCode"

fun NavGraphBuilder.productDetailsNavScreen(
    onNavigateToCheckout: () -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable(
        "$productDetailsRoute/{$productIdArgument}?promoCode={$promoCodeParameter}",
        arguments = listOf(navArgument("promoCode") { nullable = true })
    ) { navBackStack ->

        val viewModel =
            viewModel<ProductDetailsViewModel>(factory = ProductDetailsViewModel.Factory)
        val uiState by viewModel.uiState.collectAsState()

        val productId = navBackStack.arguments?.getString(productIdArgument)
        val promoCode = navBackStack.arguments?.getString(promoCodeParameter)

        Log.i("MainActivity", "promoCode: $promoCode")
        Log.i("MainActivity", "productId: $productId")

        if (promoCode == "ALURA") {
            Log.i("MainActivity", "Getting 10% discount")
        }

        productId?.let {
            ProductDetailsScreen(
                uiState = uiState,
                onOrderClick = onNavigateToCheckout,
                onRetrySearch = {
                    viewModel.findProductById(productId)
                },
                onBackClick = onPopBackStack
            )
        } ?: LaunchedEffect(Unit) {
            onPopBackStack()
        }
    }
}

fun NavController.navigateToProductDetails(id: String) {
    navigate("$productDetailsRoute/$id")
}

fun NavController.navigateToProductDetails(id: String, promoCode2: String) {
    navigate("$productDetailsRoute/$id?promoCode=$promoCode2")
}