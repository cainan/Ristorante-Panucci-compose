package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import br.com.alura.panucci.ui.screens.ProductDetailsScreen
import br.com.alura.panucci.ui.viewmodels.ProductDetailsViewModel


internal const val productDetailsRoute = "productDetails"
internal const val productIdArgument = "productId"
internal const val promoCodeParameter = "promoCode"

// alura://panucci.com.br/productDetails/9adccd9a-3918-4996-8c96-2f5b9143cef2?promoCode=PANUCCI10
// adb shell am start alura://panucci.com.br/productDetails/9adccd9a-3918-4996-8c96-2f5b9143cef2?promoCode=PANUCCI10

fun NavGraphBuilder.productDetailsNavScreen(
    onNavigateToCheckout: () -> Unit,
    onPopBackStack: () -> Unit,
) {
    composable(
        route = "$productDetailsRoute/{$productIdArgument}?$promoCodeParameter={$promoCodeParameter}",
        deepLinks = listOf(navDeepLink {
            uriPattern =
                "$uri/$productDetailsRoute/{$productIdArgument}?$promoCodeParameter={$promoCodeParameter}"
        }),
    ) { navBackStack ->

        val viewModel =
            viewModel<ProductDetailsViewModel>(factory = ProductDetailsViewModel.Factory)
        val uiState by viewModel.uiState.collectAsState()

        val productId = navBackStack.arguments?.getString(productIdArgument)
        val promoCode = navBackStack.arguments?.getString(promoCodeParameter)

        Log.i("MainActivity", "promoCode: $promoCode")
        Log.i("MainActivity", "productId: $productId")

        when (promoCode) {
            "ALURA" ->
                Log.i("MainActivity", "Getting 5% discount")

            "PANUCCI10" ->
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