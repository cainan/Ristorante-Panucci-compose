package br.com.alura.panucci.navigation

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import br.com.alura.panucci.sampledata.sampleProducts
import br.com.alura.panucci.ui.screens.ProductDetailsScreen


fun NavGraphBuilder.productDetailsNavScreen(navController: NavHostController) {
    composable(
        "${AppDestinations.ProductDetails.route}/{productId}?promoCode={promoCode}",
        arguments = listOf(navArgument("promoCode") { nullable = true })
    ) { navBackStack ->
        val productId = navBackStack.arguments?.getString("productId")
        val promoCode = navBackStack.arguments?.getString("promoCode")


        Log.i("MainActivity", "onCreate: productId: $productId")
        sampleProducts.find {
            it.id == productId
        }?.let { product ->

            if (promoCode == "ALURA") {
                Log.i("MainActivity", "Getting 10% discount")
            }

            Log.i("MainActivity", "onCreate: product: $product")
            ProductDetailsScreen(
                product = product,
                onNavigateToCheckout = {
                    navController.navigate(AppDestinations.Checkout.route)
                })
        } ?: LaunchedEffect(Unit) {
            navController.navigateUp()
        }
    }
}