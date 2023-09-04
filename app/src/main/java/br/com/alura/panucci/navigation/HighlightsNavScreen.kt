package br.com.alura.panucci.navigation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import br.com.alura.panucci.model.Product
import br.com.alura.panucci.ui.screens.HighlightsListScreen
import br.com.alura.panucci.ui.viewmodels.HighlightsListViewModel

internal const val highlightsRoute = "highlights"
fun NavGraphBuilder.highlightsNavScreen(
    onNavigateToProductDetails: (Product) -> Unit,
    onNavigateToCheckout: () -> Unit,
) {
    composable(highlightsRoute) {
        val viewModel = viewModel<HighlightsListViewModel>()
        val uiState by viewModel.uiState.collectAsState()
        HighlightsListScreen(
            uiState = uiState,
//            onNavigateToDetails = { product ->
//                val discount = "ALURA"
//                navController.navigateToProductDetails(discount, discount)
//            },
            onProductClick = onNavigateToProductDetails,
            onOrderClick = onNavigateToCheckout,
        )
    }
}

fun NavController.navigateToHighlights(navOptions: NavOptions? = null) {
    navigate(highlightsRoute, navOptions)
}