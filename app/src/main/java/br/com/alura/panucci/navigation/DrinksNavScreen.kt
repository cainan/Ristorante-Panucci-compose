package br.com.alura.panucci.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import br.com.alura.panucci.model.Product
import br.com.alura.panucci.ui.screens.DrinksListScreen
import br.com.alura.panucci.ui.viewmodels.DrinksListViewModel

internal const val drinksRoute = "drinks"

// alura://panucci.com.br/drinks
// adb shell am start alura://panucci.com.br/drinks

fun NavGraphBuilder.drinksNavScreen(onNavigateToProductDetails: (Product) -> Unit) {
    composable(drinksRoute, deepLinks = listOf(navDeepLink { uriPattern = "$uri/$drinksRoute" })) {
        val viewModel = viewModel<DrinksListViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        DrinksListScreen(
            uiState = uiState,
//            onNavigateToDetails = { product ->
//                navController.navigateToProductDetails(product.id)
//            }
            onProductClick = onNavigateToProductDetails,
        )
    }
}

fun NavController.navigateToDrinks(navOptions: NavOptions? = null) {
    navigate(drinksRoute, navOptions)
}